package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.EncodedString;

public abstract class AesEncryption {

    public static String decryptFromPasspackString(String password, EncodedString encodedString) throws Exception {
        switch (encodedString.getVersion()) {
            case 3:
                return AesEncryption_V3.decryptFromPasspackString(password, encodedString);
            case 2:
            case 1:
                return AesEncryption_V2.decryptFromPasspackString(password, encodedString);
            default:
                throw new Exception("Unsupported version");
        }
    }

    public static EncodedString encryptToPasspackString(String version, String password, String input) throws Exception {
        switch (version) {
            case "3":
                return AesEncryption_V3.encryptToPasspackString(password, input);
            default:
                throw new Exception("Unsupported version");
        }
    }

}
