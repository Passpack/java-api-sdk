package com.passpack.api.sdk.encryption;

import java.security.SecureRandom;
import java.util.Random;

public class EncryptionTools {

    /**
     * Generates a random string of the given length
     * @param length
     * @return
     */
    public static String generateRandomString(int length) {
        Random random = new SecureRandom();
        String allowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(allowed.charAt(random.nextInt(allowed.length())));
        }
        return sb.toString();

    }
}
