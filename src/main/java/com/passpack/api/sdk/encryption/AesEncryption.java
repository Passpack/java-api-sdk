package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.EncodedString;

public abstract class AesEncryption {
    public static final String LATEST_VERSION = "6";

    public static String decryptFromPasspackString(String password, EncodedString encodedString) throws Exception {
        switch (encodedString.getVersion()) {
            case 6:
                return AesEncryption_V6.decryptFromPasspackString(password, encodedString);
            case 5:
                return AesEncryption_V5.decryptFromPasspackString(password, encodedString);
            case 3:
                return AesEncryption_V3.decryptFromPasspackString(password, encodedString);
            case 2:
            case 1:
                return AesEncryption_V2.decryptFromPasspackString(password, encodedString);
            default:
                throw new Exception("Unsupported version");
        }
    }

    public static EncodedString encryptToPasspackString(String password, String input) throws Exception {
        return encryptToPasspackStringByVersion(LATEST_VERSION, password, input);
    }

    public static EncodedString encryptToPasspackStringByVersion(String version, String password, String input) throws Exception {
        switch (version) {
            case "6":
                return AesEncryption_V6.encryptToPasspackString(password, input);
            case "5":
                return AesEncryption_V5.encryptToPasspackString(password, input);
            case "3":
                return AesEncryption_V3.encryptToPasspackString(password, input);
            default:
                throw new Exception("Unsupported version");
        }
    }

}
