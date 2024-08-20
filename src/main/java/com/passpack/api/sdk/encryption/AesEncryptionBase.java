package com.passpack.api.sdk.encryption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;

public abstract class AesEncryptionBase {
    protected static final String KEY_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    protected static final String AES_ALGORITHM = "AES";
    protected static Logger log = LogManager.getLogger(AesEncryptionBase.class);

    /**
     * Generate a random salt for the encryption, version 3.
     *
     * @return
     */
    protected static byte[] generateSaltBytes(int saltLength) {
        return generateSecureRandomBytes(new byte[saltLength]);
    }

    /**
     * Generate a random IV for the encryption, version 3.
     *
     * @return
     */
    protected static byte[] generateIvBytes(int ivLength) {
        return generateSecureRandomBytes(new byte[ivLength]);
    }


    /**
     * Removes the padding from the input string. Padding would be '=' characters at the end of the string.
     * @param input
     * @return
     */
    protected static String removePaddingFromString(String input) {
        // remove all trailing equal signs
        return input.replaceAll("=+$", "");
    }
    /**
     * This is reverse engineered from a JS function in the Passpack code.  It decodes the input string into a int array.
     *
     * @param input
     * @return
     */
    protected static int[] decodeInput(String input) {
        input = removePaddingFromString(input);

        Double byteCountD = (1.0 * input.length() / 4) * 3;

        // Get the byte count which is the result dropping the decimal part of the byte count
        int byteCount = (int) Math.floor(byteCountD);
        int[] bytes = new int[byteCount];
        int j = 0;
        for (int i = 0; i < byteCount; i += 3) {
            //get the 3 octets in 4 ascii chars
            int enc1 = KEY_STRING.indexOf(getCharFromInputString(input, j));
            j++;
            int enc2 = KEY_STRING.indexOf(getCharFromInputString(input, j));
            j++;
            int enc3 = KEY_STRING.indexOf(getCharFromInputString(input, j));
            j++;
            int enc4 = KEY_STRING.indexOf(getCharFromInputString(input, j));
            j++;

            int chr1 = (enc1 << 2) | (enc2 >> 4);
            int chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            int chr3 = ((enc3 & 3) << 6) | enc4;

            bytes[i] = chr1;
            if ((i + 1 < byteCount) && enc3 != 64) bytes[i + 1] = chr2;
            if ((i + 1 < byteCount) && enc4 != 64) bytes[i + 2] = chr3;
        }

        // convert the input to byte array
        return bytes;
    }
    /**
     * Converts the int array to a byte array.
     * @param input
     * @return
     */
    public static byte[] convertIntArrayToBytes(int[] input) {
        byte[] bytes = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            bytes[i] = (byte) input[i];
        }
        return bytes;
    }
    protected static char getCharFromInputString(String input, int index) {
        char rv = '0';
        // Test to see if the index would be past the input length.
        if (index >= input.length()) {
            // we actually want the int to be 0 so we return first char, 'A'
            rv = 'A';
        } else {
            rv = input.charAt(index);
        }
        return rv;
    }


    /**
     * Generates the random bytes for the arrays.
     *
     * @param inputArray
     * @return
     */
    private static byte[] generateSecureRandomBytes(byte[] inputArray) {
        new SecureRandom().nextBytes(inputArray);
        return inputArray;
    }
}
