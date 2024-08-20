package com.passpack.api.sdk.model;

import lombok.Data;

@Data
public class Proxy {
    private final String proxyHostScheme;
    private final String proxyHost;
    private final int proxyPort;
    private final String username;
    private final char[] password;
}
