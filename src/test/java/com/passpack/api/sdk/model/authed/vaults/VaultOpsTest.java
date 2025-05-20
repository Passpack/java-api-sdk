package com.passpack.api.sdk.model.authed.vaults;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.passpack.api.model.publicApi.secure.password.*;
import com.passpack.api.model.publicApi.secure.passwordVault.ListPasswordVaultResponse;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.model.authed.BaseAuthedTest;
import com.passpack.api.sdk.model.authed.account.PackingKey;
import com.passpack.api.sdk.model.authed.account.PackingKeyOps;
import com.passpack.api.sdk.model.authed.team.TeamOps;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class VaultOpsTest extends BaseAuthedTest {

    @Test
    public void testListVaults() throws Exception {
        HttpResponseObject<ListPasswordVaultResponse> response = VaultOps.listVaults();
        assertSuccessfulPasspackResponseObject(response);
    }


//    @Test
//    public void testRetrieveAndDecryptVaults() throws Exception {
//        HttpResponseObject<ListPasswordVaultResponse> response = VaultOps.listVaults();
//
//        PackingKey packingKey = PackingKeyOps.retrieveAndDecryptPackingKey();
//
//        List<DecryptedPasswordVault> decodedVaults = VaultOps.decryptVaults(response.getReturnObject().getVaults(), packingKey);
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(decodedVaults));
//
//    }


}
