package com.passpack.api.sdk.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ReflectionAccessFilter;
import com.passpack.api.model.publicApi.PasspackResponseObject;
import com.passpack.api.model.publicApi.headers.PasspackHeaderNames;
import com.passpack.api.model.publicApi.system.Telemetry;
import com.passpack.api.sdk.Passpack;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.model.*;
import com.passpack.api.sdk.model.noauth.CsrfTokenOps;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class APIDriven {
    protected static Logger log = LogManager.getLogger(APIDriven.class);
    private final static ObjectMapper OM = new ObjectMapper();
    private static RequestConfig requestConfig = null;
    private static boolean logClient = true;
    private static final Gson INTERNAL_GSON = createGson(false);
    private static String currentCsrfToken = null;
    private static final String CSRF_TOKEN_HEADER_NAME = "x-csrf-token";
    private static BasicCookieStore cookieStore = new BasicCookieStore();
    private static HttpClientContext context = HttpClientContext.create();
    private static boolean initialized =false;
    private static final String PASSPACK_USER_AGENT = "Passpack-SDK";

    public APIDriven() {
        init();
    }

    private static void init() {
        boolean shouldLoadCsrf = false;

        // Initialize the context
        if (!initialized) {
            log.info("SETTING COOKIE STORE");
            context.setCookieStore(cookieStore);
            shouldLoadCsrf = shouldReloadCsrf();
            initialized = true;
        }

        if (shouldLoadCsrf) {
            reloadCsrfToken();
        }
    }

    /**
     * Generates the default request options with the specified resource path
     * @param resourcePath
     * @return
     */
    protected static RequestOptions getDefaultOptions(String resourcePath) {

        if (null == resourcePath || resourcePath.isEmpty()) {
            throw new IllegalArgumentException("Resource path cannot be null or empty");
        }
        if (resourcePath.charAt(0) != '/') {
            resourcePath = "/" + resourcePath;
        }
        RequestOptions options = new RequestOptions();
        options.setApiKey(Passpack.apiKey);
        options.setApiSecret(Passpack.apiSecret);
        options.setApiEndpoint(new APIEndpoint(new APIHost(Passpack.getApiBase()), resourcePath));
        options.setUserAgent(PASSPACK_USER_AGENT);
        return options;
    }


    static HttpHeaders buildHeaders(APIRequest request) {
        Map<String, List<String>> headerMap = new HashMap<String, List<String>>();
        // CSRF
        if (null != currentCsrfToken) {
            headerMap.put(CSRF_TOKEN_HEADER_NAME, Arrays.asList(currentCsrfToken));
        }

        headerMap.put(
                PasspackHeaderNames.HEADER_NAME_USER_AGENT,
                Arrays.asList(buildUserAgentString()));
        headerMap.put(
                PasspackHeaderNames.HEADER_NAME_CLIENT_USER_AGENT,
                Arrays.asList(buildXPasspackClientUserAgentString()));


        headerMap.put(
                PasspackHeaderNames.HEADER_NAME_CLIENT_VERSION,
                Arrays.asList(Passpack.CLIENT_VERSION));

        headerMap.put(
                PasspackHeaderNames.HEADER_NAME_CLIENT_REQUESTED_API_VERSION,
                Arrays.asList(Passpack.API_VERSION));

        return HttpHeaders.of(headerMap);
    }



    protected static String buildUserAgentString() {
        String userAgent = String.format("Passpack/v1 JavaBindings/%s", Passpack.CLIENT_VERSION);

        if (Passpack.getAppInfo() != null) {
            userAgent += " " + formatAppInfo(Passpack.getAppInfo());
        }

        return userAgent;
    }

    private static String formatAppInfo(Map<String, String> info) {
        String str = info.get("name");

        if (info.get("version") != null) {
            str += String.format("/%s", info.get("version"));
        }

        if (info.get("url") != null) {
            str += String.format(" (%s)", info.get("url"));
        }

        return str;
    }

    protected static String buildXPasspackClientUserAgentString() {
        String[] propertyNames = {
                "os.name",
                "os.version",
                "os.arch",
                "java.version",
                "java.vendor",
                "java.vm.version",
                "java.vm.vendor"
        };

        Map<String, String> propertyMap = new HashMap<>();
        for (String propertyName : propertyNames) {
            propertyMap.put(propertyName, System.getProperty(propertyName));
        }
        propertyMap.put("bindings.version", Passpack.CLIENT_VERSION);
        propertyMap.put("lang", "Java");

        if (Passpack.getAppInfo() != null) {
            propertyMap.put("application", INTERNAL_GSON.toJson(Passpack.getAppInfo()));
        }

        return INTERNAL_GSON.toJson(propertyMap);
    }

    private static void reloadCsrfToken() {
        log.info("Reloading CSRF");
        CsrfTokenOps.retrieveCsrfToken();
    }

    private static boolean shouldReloadCsrf() {
        if (null == currentCsrfToken || currentCsrfToken.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    protected static <T extends PasspackResponseObject> HttpResponseObject<T> sendRequest(
            APIRequest apiRequest,
            Object requestObject,
            Class<T> clazz,
            boolean ignoreResponse) {

        Telemetry telemetry = new Telemetry();
        if (!initialized) init();

        log.info("CSRF: {}, for {}", currentCsrfToken, apiRequest.getOptions().getApiEndpoint().getPath());
        if (shouldReloadCsrf()) {
            // Get the CSRF token
            reloadCsrfToken();
        }

        // Send the request
        HttpResponseObject<T> response =  sendRequestAfterCsrfCheck(apiRequest, requestObject, clazz, ignoreResponse);
        telemetry.markEnd();
        logIfApiWarning(response);
//        log.info("Elapsed time: {} ms", (telemetry.get());
        response.getReturnObject().setTelemetry(telemetry);
        return response;
    }

    protected static void logIfApiWarning(HttpResponseObject response) {
        String headerValue = response.getFirstHeaderValue(PasspackHeaderNames.HEADER_NAME_CLIENT_VERSION_WARNING);
        if (null != headerValue && !headerValue.isEmpty()) {
            log.warn("API Warning: {}", headerValue);
        }

    }

    protected static <T extends PasspackResponseObject> HttpResponseObject<T> sendRequestGetCsrf(
            APIRequest apiRequest,
            Object requestObject,
            Class<T> clazz,
            boolean ignoreResponse) {

        // Send the request
        return sendRequestAfterCsrfCheck(apiRequest, requestObject, clazz, ignoreResponse);
    }

    private static <T extends PasspackResponseObject> HttpResponseObject<T> sendRequestAfterCsrfCheck(
            APIRequest apiRequest,
            Object requestObject,
            Class<T> clazz,
            boolean ignoreResponse) {


        HttpResponseObject responseObject = new HttpResponseObject();
        T returnObject = null;
        CloseableHttpClient httpclient = generateClient(apiRequest.getOptions().getProxyDetails());
        CloseableHttpResponse response = null;
        HttpEntity httpEntity = null;

        try {


            HttpUriRequestBase request;
            URIBuilder uriBuilder = new URIBuilder(apiRequest.getOptions().getApiEndpoint().getURL());
            // Add any parameters
            if (null != apiRequest.getQueryParams()) {
                apiRequest.getQueryParams().forEach((k, v) -> uriBuilder.addParameter(k, v.toString()));
            }

            // special case for GET - add a cachebuster header
            if (apiRequest.getMethod() == RequestMethod.GET) {
                uriBuilder.addParameter("buster", String.valueOf(System.currentTimeMillis()));
            }


            // Generate the URI
            URI targetUri = uriBuilder.build();

            if (logClient) log.info("Connecting to {}", targetUri.toString());
            switch (apiRequest.getMethod()) {
                case GET:
                    request = new HttpGet(targetUri);
                    break;
                case POST:
                    request = new HttpPost(targetUri);
                    break;
                case PUT:
                    request = new HttpPut(targetUri);
                    break;
                case DELETE:
                    request = new HttpDelete(targetUri);
                    break;
                default:
                    request = null;
            }

            if (null == request) {
                log.error("Error creating request. Request is null");
            } else {
                if (null != requestConfig) request.setConfig(requestConfig);

                if (null != requestObject) {
                    StringEntity input = new StringEntity(OM.writeValueAsString(requestObject), ContentType.APPLICATION_JSON);
                    request.setEntity(input);
                }

                // Set headers
                HttpHeaders headers = buildHeaders(apiRequest);
                if (null != headers) {
                    headers.map().forEach((k, v) -> {
                        if (v.size() == 1) {
                            request.addHeader(k, v.get(0)  );
                            if (Passpack.logRequestHeaders) log.info(" ---> Request Header: {} = {}", k, v.get(0));
                        } else {
                            if (Passpack.logRequestHeaders) log.info(" ---> Request Header: {} = {}", k, v);
                            request.addHeader(k, v);
                        }

                    });

                    // Check out the cookie store
                    if (null != cookieStore) {
                        log.info("cookies to send: {}", cookieStore.toString());
                    }

                    // Authentication
                    if (null != apiRequest.getOptions().getApiKey()) {
                        log.info("API KEY: {}", apiRequest.getOptions().getApiKey());
                        request.addHeader(PasspackHeaderNames.HEADER_NAME_API_KEY, apiRequest.getOptions().getApiKey());
                        request.addHeader(PasspackHeaderNames.HEADER_NAME_API_SECRET, apiRequest.getOptions().getApiSecret());
                    }
                }

                response = httpclient.execute(request ,context);

                // Peek at the cookies
                try {
                    final List<Cookie> cookies = cookieStore.getCookies();
                    System.out.println("Cookie Store Contents:");
                    for (int i = 0; i < cookies.size(); i++) {
                        System.out.println("\t" + cookies.get(i));
                    }
                } catch (Exception e) {}

                httpEntity = response.getEntity();


                if (logClient) log.info("rc: " + response.getCode());
                if (response.getCode() > 299 || response.getCode() < 200) {
                    log.error("Failed : HTTP error code : "
                            + response.getCode() + ", URL: " + apiRequest.getOptions().getApiEndpoint().getURL());
                    logResponseError(httpEntity);
                } else {
                    if (!ignoreResponse) returnObject = readResponseIntoObject(httpEntity.getContent(), clazz);
                }

                // Get headers
                responseObject.setHeaders(response.getHeaders());
                if (logClient && Passpack.logResponseHeaders) {
                    log.info("Response has {} headers", responseObject.getHeaders().length);
                    for (int i = 0; i < responseObject.getHeaders().length; i++) {
                        log.info(" <--- Response Header: {}", responseObject.getHeaders()[i]);
                    }
                }

                // capture the CSRF Token if present
                String csrfToken = responseObject.getFirstHeaderValue(CSRF_TOKEN_HEADER_NAME);
//                log.info("CSRF: {}", csrfToken);
                if (null != csrfToken && !csrfToken.isEmpty()) currentCsrfToken = csrfToken;
            }

        } catch (Exception e) {
            log.error("error while getting url {}", apiRequest.getOptions().getApiEndpoint().getURL(), e);
        } finally {
            try {
                // ensure we can close
                EntityUtils.consumeQuietly(httpEntity);
                if (null != response) {
                    response.close();
                }
            } catch (Exception e) {
            }
            try {
                httpclient.close();
            } catch (Exception e) {
                log.error("error closing client", e);
            }
        }

        responseObject.setHeaders(response.getHeaders());

        responseObject.setReturnObject(returnObject);
        return responseObject;
    }

    private static <T> T readResponseIntoObject(InputStream is, Class clazz) throws Exception {
        Type genericType = clazz;
        T returnObject = OM.readValue(is, OM.constructType(genericType));
        is.close();
        return returnObject;
    }

    private static void logResponseError(HttpEntity entity) throws Exception {
        log.error(EntityUtils.toString(entity));
    }

    /**
     * Generate a client with the proxy details
     *
     * @param proxyDetails
     * @return
     */
    private static CloseableHttpClient generateClient(Proxy proxyDetails) {
        CloseableHttpClient httpclient = null;
        try {

            if (null != proxyDetails
                    && !proxyDetails.getProxyHost().isEmpty()) {
                BasicCredentialsProvider proxyCredsProvider = null;
                // Create the proxy host object
                final HttpHost proxyHost = new HttpHost(proxyDetails.getProxyHostScheme(), proxyDetails.getProxyHost(), proxyDetails.getProxyPort());

                // If the proxyDetail contains a username and non-empty password, create a credentials provider
                if (!proxyDetails.getUsername().isEmpty()
                        && (null != proxyDetails.getPassword() && proxyDetails.getPassword().length > 0)) {
                    proxyCredsProvider = new BasicCredentialsProvider();
                    AuthScope proxyAuthScope = new AuthScope(proxyHost);
                    proxyCredsProvider.setCredentials(proxyAuthScope, new UsernamePasswordCredentials(proxyDetails.getUsername(), proxyDetails.getPassword()));
                }


                httpclient = HttpClients.custom()
                        .setConnectionManagerShared(true)
                        .setProxy(proxyHost)
                        .setDefaultCredentialsProvider(proxyCredsProvider)
                        .setDefaultCookieStore(cookieStore)

                        .build();

            } else {
                httpclient = HttpClients.custom()
                        .setDefaultCookieStore(cookieStore)
                        .build();
            }
        } catch (Exception e) {
            log.error("Error generating client", e);
        }
        return httpclient;
    }

    private static Gson createGson(boolean shouldSetResponseGetter) {
        GsonBuilder builder =
                new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .addReflectionAccessFilter(
                                new ReflectionAccessFilter() {
                                    @Override
                                    public ReflectionAccessFilter.FilterResult check(Class<?> rawClass) {
                                        if (rawClass.getTypeName().startsWith("com.passpack.")) {
                                            return ReflectionAccessFilter.FilterResult.ALLOW;
                                        }
                                        return ReflectionAccessFilter.FilterResult.BLOCK_ALL;
                                    }
                                });

        return builder.create();
    }
}
