package com.passpack.api.sdk.model.noauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.passpack.api.model.publicApi.csrf.CsrfResponse;
import com.passpack.api.model.publicApi.system.SiteMessageListResponse;
import com.passpack.api.sdk.model.HttpResponseObject;
import org.junit.jupiter.api.Test;

public class SiteMessagesOpsTest {

    @Test
    public void test() throws Exception {
        HttpResponseObject<SiteMessageListResponse>  response = SiteMessagesOps.retrieve();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(response));
    }
}
