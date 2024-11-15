package com.passpack.api.sdk.model.authed.account;

import com.passpack.api.model.publicApi.paths.PortalUrlPaths;
import com.passpack.api.model.publicApi.secure.account.GetKeyDataRequest;
import com.passpack.api.model.publicApi.secure.account.GetKeyDataResponse;
import com.passpack.api.sdk.Passpack;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.encryption.AesEncryption;
import com.passpack.api.sdk.model.APIRequest;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.model.encryption.EncodedStringFactory;
import com.passpack.api.sdk.net.APIDriven;
import com.passpack.api.sdk.net.RequestMethod;

import java.util.Map;

public class PackingKeyOps extends APIDriven {
    private static final String PACKING_KEY_CHECK_VALUE = "c42963b150bf47ad";

    /**
     * This method retrieves the packing key from the server and decrypts it returning an object
     * which has the decoded private key available.
     * @return
     * @throws Exception
     */
    public static PackingKey retrieveAndDecryptPackingKey() throws Exception {
        HttpResponseObject<GetKeyDataResponse> response = retrieve();
        GetKeyDataResponse data = response.getReturnObject();
        assert response != null;
        assert data.getStatus().getCode() == 0;

//        // Create the packing key object
        PackingKey packingKey = new PackingKey();
        packingKey.setEncodedString(EncodedStringFactory.createFromInputString(data.getPrivateKey()));
        packingKey.setPublicKey(data.getPublicKey());
        packingKey.setEncodedePrivateKey(data.getPrivateKey());


        // Decode the data element - this is used to ensure proper passphrase entry
        String decodedData = AesEncryption.decryptFromPasspackString(Passpack.apiPackingKey, EncodedStringFactory.createFromInputString(data.getData()));
        if (!decodedData.equals(PACKING_KEY_CHECK_VALUE)) {
            throw new Exception("Invalid packing key");
        }
        // Now decode the actual private key
        packingKey.setDecodedPrivateKey(
                AesEncryption.decryptFromPasspackString(
                        Passpack.apiPackingKey, EncodedStringFactory.createFromInputString(data.getPrivateKey())).trim());


        return packingKey;
    }

    /**
     * @return
     */
    public static HttpResponseObject<GetKeyDataResponse> retrieve() {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.ACCOUNT_KEY_DATA_PATH);
        RequestMethod method = RequestMethod.GET;
        Map<String, Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, new GetKeyDataRequest(), GetKeyDataResponse.class, ignoreResponse);
    }
}
