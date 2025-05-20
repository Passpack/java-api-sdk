package com.passpack.api.sdk.model.encryption;

public interface EncodedString {
    public int getVersion() ;
    public String getIv() ;
    public String getSalt() ;
    public String getCipherText();
    public String toString();
    public int getIterations();
}
