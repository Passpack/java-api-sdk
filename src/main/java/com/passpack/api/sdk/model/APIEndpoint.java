package com.passpack.api.sdk.model;

import lombok.Data;

@Data
public class APIEndpoint {
    private final APIHost apiHost;
    private final String path;


    /**
     * Get the full URL of the endpoint
     * @return
     */
    public String getURL() {
        return apiHost.getEndpoint() + path;
    }

}
