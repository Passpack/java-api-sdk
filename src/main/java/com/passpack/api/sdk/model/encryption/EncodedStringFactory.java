package com.passpack.api.sdk.model.encryption;

import com.passpack.api.sdk.encryption.AesEncryption_V5;
import com.passpack.api.sdk.encryption.AesEncryption_V6;

public class EncodedStringFactory {

    public static EncodedString createFromInputString(String input) throws Exception {
        // Try and determine the delimiter.  It is formatted in the following way: version#delimiter#data  The delimiter
        // could be a "#" or a "|"
        String delimiter = input.substring(1, 2);
        if (!delimiter.equals("#") && !delimiter.equals("|")) {
            throw new Exception("Unsupported delimiter");
        }
        // if it is a pipe then we need to escape it
        if (delimiter.equals("|")) {
            delimiter = "\\|";
        }
        return createFromInputString(input, delimiter);
    }

    public static EncodedString createFromInputString(String input, String delimiter) throws Exception {
        switch (getVersion(input, delimiter)) {
            case 6:
                return new EncodedStringv5(input, delimiter, AesEncryption_V6.V6_ITERATIONS_DEFAULT);
            case 5:
                return new EncodedStringv5(input, delimiter, AesEncryption_V5.V5_ITERATIONS_DEFAULT);
            case 3:
                return new EncodedStringv3(input, delimiter);
            case 2:
            case 1:
                return new EncodedStringv2(input, delimiter);
            default:
                throw new Exception("Unsupported version");
        }
    }


    /**
     * Get the version of the encoded string
     *
     * @param encodedString
     * @return
     */
    private static int getVersion(String encodedString, String delimiter) {
        return Integer.parseInt(encodedString.split(delimiter)[0]);
    }
}
