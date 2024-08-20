package com.passpack.api.sdk.model.noauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.passpack.api.model.publicApi.csrf.CsrfResponse;
import com.passpack.api.sdk.model.HttpResponseObject;
import org.junit.jupiter.api.Test;

public class CsrfTokenOpsTest {

    @Test public void test() throws Exception {
            HttpResponseObject<CsrfResponse> response = CsrfTokenOps.retrieveCsrfToken();
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(response));
    }
}
