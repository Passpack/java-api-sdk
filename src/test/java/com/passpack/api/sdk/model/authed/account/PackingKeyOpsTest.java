package com.passpack.api.sdk.model.authed.account;

import com.passpack.api.model.publicApi.secure.account.GetKeyDataResponse;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.model.authed.BaseAuthedTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PackingKeyOpsTest extends BaseAuthedTest {

    @Test
    public void testRetrievePackingKey() throws Exception {
        HttpResponseObject<GetKeyDataResponse> response = PackingKeyOps.retrieve();
        assertSuccessfulPasspackResponseObject(response);
    }

    @Test
    public void testRetrieveAndDecryptPackingKey() throws Exception {
        PackingKey packingKey = PackingKeyOps.retrieveAndDecryptPackingKey();
        Assertions.assertNotNull(packingKey);

        // Log some values.
        System.out.println("Decoded Private Key: " + packingKey.getDecodedPrivateKey());
    }
}
