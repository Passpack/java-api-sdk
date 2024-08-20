package com.passpack.api.sdk.model.encryption;

import lombok.Data;

@Data
public abstract class EncodedStringBaseClass {
    protected static final int UNKNOWN_VERSION = -1;
    protected static final int PART_VERSION = 0;
    protected static final int PART_IV = 1;
    protected static final int PART_SALT = 2;
    protected static final int PART_CIPHER_TEXT = 3;
    protected volatile String[] parts;
    protected volatile int partCount = -1;
    protected volatile boolean parsed = false;
    protected String encodedString;
    protected String delimiter = "#";

    /**
     * Get the version of the encoded string
     * @return
     */
    public int getVersion() {
        if (!parsed) {
            parse();
        }
        return partCount > 0?Integer.parseInt(getParts()[PART_VERSION]):UNKNOWN_VERSION;
    }

    /**
     * Parse the encoded string into its parts
     */
    abstract void parse() ;

    protected String[] getParts() {
        return encodedString.split(delimiter);
    }

}
