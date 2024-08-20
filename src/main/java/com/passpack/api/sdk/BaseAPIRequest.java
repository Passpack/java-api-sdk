package com.passpack.api.sdk;

import com.passpack.api.sdk.net.RequestMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseAPIRequest {
    private  RequestOptions options;
    private  RequestMethod method;
    private  Map<String, Object> queryParams;
}
