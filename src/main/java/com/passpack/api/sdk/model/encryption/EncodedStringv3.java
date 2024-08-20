package com.passpack.api.sdk.model.encryption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder
@AllArgsConstructor
public class EncodedStringv3 extends EncodedStringBaseClass implements EncodedString {
    private String iv;
    private String salt;
    private String cipherText;

public EncodedStringv3(String encodedString, String delimiter) {
        super();
        super.delimiter   = delimiter;
        this.encodedString = encodedString;
        parse();
    }

    public EncodedStringv3(String delimiter, String iv, String salt, String cipherText) {
        super();
        super.delimiter   = delimiter;
        this.iv = iv;
        this.salt = salt;
        this.cipherText = cipherText;
        encodedString = toString();
        parse();
    }

    void parse () {
        parts = getParts();
        partCount = parts.length;
        iv = partCount >= 1?parts[PART_IV]:null;
        salt = partCount >= 2?parts[PART_SALT]:null;
        cipherText = partCount >= 3?parts[PART_CIPHER_TEXT]:null;
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

    @Override
    public String toString() {
        return  "3" + delimiter + iv + delimiter + salt + delimiter + cipherText;
    }


}
