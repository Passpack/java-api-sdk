package com.passpack.api.sdk.model.noauth;

import com.passpack.api.model.publicApi.paths.PortalUrlPaths;
import com.passpack.api.model.publicApi.system.SiteMessageListRequest;
import com.passpack.api.model.publicApi.system.SiteMessageListResponse;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.model.APIRequest;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.net.APIDriven;
import com.passpack.api.sdk.net.RequestMethod;

import java.util.Map;

public class SiteMessagesOps extends APIDriven {


    public static HttpResponseObject<SiteMessageListResponse> retrieve() {
        RequestOptions options = getDefaultOptions("/v1/siteMessages");
        RequestMethod method = RequestMethod.GET;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, new SiteMessageListRequest(), SiteMessageListResponse.class, ignoreResponse);

    }
}
