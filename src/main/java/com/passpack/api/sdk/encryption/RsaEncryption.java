package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.RsaKeyMaterial;

public abstract class RsaEncryption {

    public static String decryptWithPasspackJWK(RsaKeyMaterial rsaKeyMaterial, String encodedString) throws Exception {
        switch (rsaKeyMaterial.getVersion()) {

            case 2:
            case 1:
                return RsaEncryption_V2.decryptWithPasspackJWK(rsaKeyMaterial, encodedString);
            default:
                throw new Exception("Unsupported version");
        }
    }

    public static String encryptWithPasspackJWK(RsaKeyMaterial rsaKeyMaterial, String inputToEncrypt) throws Exception {
        switch (rsaKeyMaterial.getVersion()) {
            case 2:
                return RsaEncryption_V2.encryptWithPasspackJWK(rsaKeyMaterial, inputToEncrypt);
            default:
                throw new Exception("Unsupported version");
        }
    }

}
