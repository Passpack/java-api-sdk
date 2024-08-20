package com.passpack.api.sdk.model.authed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.passpack.api.model.publicApi.PasspackResponseObject;
import com.passpack.api.sdk.Passpack;
import com.passpack.api.sdk.model.HttpResponseObject;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseAuthedTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setup() {
//        Passpack.apiSecret = "";
//        Passpack.apiKey = "";
//        Passpack.apiPackingKey = "";
    }

    /**
     * Checks the response object for null and a successful status code
     * @param response
     * @throws Exception
     */
    protected void assertSuccessfulPasspackResponseObject(HttpResponseObject response) throws Exception{

        assert response != null;
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));

        assert response.getReturnObject().getStatus().getCode() ==0;
    }
}
