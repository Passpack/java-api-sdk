package com.passpack.api.sdk.net;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class HttpHeaders {
    private Map<String, List<String>> headerMap;
    private HttpHeaders(Map<String, List<String>> headerMap) {
        this.headerMap = headerMap;
    }

    public HttpHeaders withAdditionalHeaders(Map<String, List<String>> headerMap) {
        requireNonNull(headerMap);
        Map<String, List<String>> newHeaderMap = new HashMap<>(this.map());
        newHeaderMap.putAll(headerMap);
        return HttpHeaders.of(newHeaderMap);
    }

    /**
     * Generates an instance from the given map.
     * @param headerMap
     * @return
     */
    public static HttpHeaders of(Map<String, List<String>> headerMap) {
        requireNonNull(headerMap);
        return new HttpHeaders(headerMap);
    }

    /**
     * Generates an unmodifiable map. forces a re-creation of the map to ensure immutability.
     * @return
     */
    public Map<String, List<String>> map() {
        return Collections.unmodifiableMap(this.headerMap);
    }
}
