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

        // Check and see if the API settings are available in the environment
        if (System.getenv("PASSPACK_API_KEY") != null) {
            Passpack.apiKey = System.getenv("PASSPACK_API_KEY");
            System.out.println("Setting API Key=" + Passpack.apiKey);
        }
        if (System.getenv("PASSPACK_API_SECRET") != null) {
            Passpack.apiSecret = System.getenv("PASSPACK_API_SECRET");
            System.out.println("Setting API Secret=<<>>");
        }
        if (System.getenv("PASSPACK_API_PACKING_KEY") != null) {
            Passpack.apiPackingKey = System.getenv("PASSPACK_API_PACKING_KEY");
            System.out.println("Setting API Packing Key=<<>>");
        }
        if (System.getenv("PASSPACK_API_BASE") != null) {
            Passpack.apiBase = System.getenv("PASSPACK_API_BASE");
            System.out.println("Setting API Base=" + Passpack.apiBase);
        }
    }

    /**
     * Checks the response object for null and a successful status code
     * @param response
     * @throws Exception
     */
    protected void assertSuccessfulPasspackResponseObject(HttpResponseObject response) throws Exception{

        assert response != null;
        int apiStatusCode = response.getReturnObject().getStatus().getCode();
        System.out.println("--------------------------- Begin Response API Code: " + apiStatusCode + " ---------------------------");
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));

        assert apiStatusCode == 0;
    }
}
