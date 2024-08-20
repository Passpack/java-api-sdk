package com.passpack.api.sdk;

import com.passpack.api.sdk.model.APIEndpoint;
import com.passpack.api.sdk.model.Proxy;
import lombok.Data;

@Data
public class RequestOptions {
    private String apiKey;
    private String apiSecret;
    private APIEndpoint apiEndpoint;
    private String userAgent;
    private String apiVersion = Passpack.API_VERSION;
    private String clientVersion = Passpack.CLIENT_VERSION;
    private Proxy proxyDetails;
}
