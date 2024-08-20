package com.passpack.api.sdk.model;

import com.passpack.api.sdk.BaseAPIRequest;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.net.HttpHeaders;
import com.passpack.api.sdk.net.RequestMethod;
import lombok.Data;

import java.util.Map;

@Data
public class APIRequest extends BaseAPIRequest {
    HttpHeaders headers ;

    public APIRequest(RequestOptions options, RequestMethod method, Map<String, Object> queryParams) {
        super(options, method, queryParams);
    }

    public APIRequest() {
        super(null, null, null);
    }



}
