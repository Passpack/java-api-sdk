package com.passpack.api.sdk.model;

import lombok.Data;

@Data
public class APIHost {
    private final String endpoint;
    // We are only supporting TLS
    private int port = 443;
    private String scheme = "https";

}
