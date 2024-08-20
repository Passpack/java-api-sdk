package com.passpack.api.sdk;

import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.Map;

public abstract class Passpack {

    public static final String API_VERSION = APIVersion.CURRENT;
    public static final String PORTAL_API_BASE = "https://api.passpack.com/portal";
    public static final String CLIENT_VERSION = ClientVersion.CURRENT;
    private static volatile Map<String, String> appInfo = null;


    /**
     * API key for authenticating requests.
     */
    public static volatile String apiKey = "PleaseChangeFromDefaultKey";
    public static volatile String apiSecret = "PleaseChangeFromDefaultSecret";
    public static volatile String apiPackingKey = "PleaseChangeFromDefaultPackingKey";

    /**
     * Client related settings
     */
    public static volatile String apiBase = PORTAL_API_BASE;
    private static volatile Proxy connectionProxy = null;
    private static volatile PasswordAuthentication proxyCredential = null;
    public static String getApiBase() {
        return apiBase;
    }

    /*
     * Logging
     */
    public static boolean logRequestHeaders = false;
    public static boolean logResponseHeaders = false;

    //------------------------------------------------//
    //-------------------- Proxy ---------------------//
    //------------------------------------------------//
    /**
     * Set proxy to tunnel all Passpack connections, if required.
     *
     * @param proxy proxy object with host and port
     */
    public static void setConnectionProxy(final Proxy proxy) {
        connectionProxy = proxy;
    }
    public static Proxy getConnectionProxy() {
        return connectionProxy;
    }

    /**
     * Set a credential for proxy authorization, if required.
     *
     * @param auth passwordAuthentication object with username and password
     */
    public static void setProxyCredential(final PasswordAuthentication auth) {
        proxyCredential = auth;
    }
    public static PasswordAuthentication getProxyCredential() {
        return proxyCredential;
    }


    //------------------------------------------------//
    //------------------- APP INFO--------------------//
    //------------------------------------------------//
//    public static void setAppInfo(String name, String version, String url, String partnerId) {
//        if (appInfo == null) {
//            appInfo = new HashMap<String, String>();
//        }
//
//        appInfo.put("name", name);
//        appInfo.put("version", version);
//        appInfo.put("url", url);
//        appInfo.put("partner_id", partnerId);
//    }

    public static Map<String, String> getAppInfo() {
        return appInfo;
    }
}
