package com.passpack.api.sdk.model.encryption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EncodedStringv5 extends EncodedStringBaseClass implements EncodedString {
    private String iv;
    private String salt;
    private int iterations;
    private String cipherText;

    public EncodedStringv5(String encodedString, String delimiter, int iterations) {
        super();
        super.delimiter = delimiter;
        this.encodedString = encodedString;
        this.iterations = iterations;
        parse();
    }

    public EncodedStringv5(String delimiter, String iv, String salt, int interations, String cipherText) {
        super();
        super.delimiter = delimiter;
        this.iv = iv;
        this.salt = salt;
        this.cipherText = cipherText;
        this.iterations = interations;
        encodedString = toString();
        parse();
    }

    void parse() {
        parts = getParts();
        partCount = parts.length;
        iterations = partCount >= 1 ? Integer.parseInt(parts[PART_V5_ITERATIONS]) : 0;
        iv = partCount >= 2 ? parts[PART_V5_IV] : null;
        salt = partCount >= 3 ? parts[PART_V5_SALT] : null;
        cipherText = partCount >= 4 ? parts[PART_V5_CIPHER_TEXT] : null;
        parsed = true;
    }

    public String getIv() {
        if (!parsed) {
            parse();
        }
        return iv;
    }

    public String getSalt() {
        if (!parsed) {
            parse();
        }
        return salt;
    }

    public String getCipherText() {
        if (!parsed) {
            parse();
        }
        return cipherText;
    }

    public int getIterations() {
        if (!parsed) {
            parse();
        }
        return iterations;
    }

    @Override
    public String toString() {
        encodedString =  "5" + delimiter + iterations + delimiter + iv + delimiter + salt + delimiter + cipherText;
        return encodedString;
    }


}
