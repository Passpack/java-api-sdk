package com.passpack.api.sdk.model;


import com.passpack.api.model.publicApi.PasspackResponseObject;
import org.apache.hc.core5.http.Header;

public class HttpResponseObject<T extends PasspackResponseObject> {
    private Header[] headers;
    private T returnObject;

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public T getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(T returnObject) {
        this.returnObject = returnObject;
    }

    public String getFirstHeaderValue(String name) {
        if (null == headers) {
            return "";
        } else {
            for (Header h : headers) {
                if (h.getName().equals(name)) {
                    return h.getValue();
                }
            }
            return "";
        }
    }
}
