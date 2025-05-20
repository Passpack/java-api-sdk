package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.RsaKeyMaterial;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.nio.charset.StandardCharsets;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;

public class RsaEncryption_V3 {
    protected static Logger LOG = LogManager.getLogger(RsaEncryption_V3.class);
    private static OAEPParameterSpec OAEP_PARAMS;
    private static boolean initialized = false;

    public static void init() {
        LOG.info("Initializing RsaEncryption_V3");
        OAEP_PARAMS = new OAEPParameterSpec(
                "SHA-512",
                "MGF1",
                new MGF1ParameterSpec("SHA-512"),
                new PSource.PSpecified("zz".getBytes(StandardCharsets.UTF_8))
        );
        initialized = true;
    }


    /**
     * Decrypt the encoded data with rsa key material from the passpackApp.
     *
     * @param keyMaterial
     * @param encodedData
     * @return
     * @throws Exception
     */
    public static String decryptWithPasspackJWK(RsaKeyMaterial keyMaterial, String encodedData) throws Exception {
        String result = "";
        if (!initialized) init();

        Cipher rsa = Cipher.getInstance("RSA/ECB/OAEPPadding");
        rsa.init(Cipher.DECRYPT_MODE, keyMaterial.getRsaPrivateKeyFromPasspackJWK(), OAEP_PARAMS);
        byte[] decodedDataArray = Base64.getDecoder().decode(encodedData);
        byte[] decrypted = rsa.doFinal(decodedDataArray);
        // Convert back to string
        result = new String(decrypted);
        return result;
    }

    public static String encryptWithPasspackJWK(RsaKeyMaterial keyMaterial, String dataToEncode) throws Exception {
        if (!initialized) init();

        Cipher rsaOAEPEnc = Cipher.getInstance("RSA/ECB/OAEPPadding");
        rsaOAEPEnc.init(Cipher.ENCRYPT_MODE, keyMaterial.getRsaPublicKeyFromPasspackJWK(), OAEP_PARAMS);
        byte[] encrypted = rsaOAEPEnc.doFinal(dataToEncode.getBytes(StandardCharsets.UTF_8));
        String encodedData = Base64.getEncoder().encodeToString(encrypted);

        return encodedData;
    }




}
