package com.passpack.api.sdk.model.noauth;

import com.passpack.api.model.publicApi.paths.PortalUrlPaths;
import com.passpack.api.model.publicApi.csrf.CsrfRequest;
import com.passpack.api.model.publicApi.csrf.CsrfResponse;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.model.APIRequest;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.net.APIDriven;
import com.passpack.api.sdk.net.RequestMethod;

import java.util.Map;

public class CsrfTokenOps extends APIDriven {


    /**
     * Retrieve CSRF token.  This will also capture the CSRF token for later API calls which would require it.
     * @return
     */
    public static HttpResponseObject<CsrfResponse> retrieveCsrfToken() {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.CSRF_GENERATOR_PATH);
        RequestMethod method = RequestMethod.GET;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequestGetCsrf(apiRequest, new CsrfRequest(), CsrfResponse.class, ignoreResponse);
    }
}
