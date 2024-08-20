package com.passpack.api.sdk.model.encryption;

import lombok.Data;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAMultiPrimePrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Data
public abstract class RsaKeyMaterialBaseClass {
    protected static final int UNKNOWN_VERSION = -1;
    protected static final int PART_VERSION = 0;
    protected static final int PART_M = 1;
    protected static final int PART_E = 2;
    protected static final int PART_D = 3;
    protected static final int PART_P = 4;
    protected static final int PART_Q = 5;
    protected static final int PART_DP = 6;
    protected static final int PART_DQ = 7;
    protected static final int PART_U = 8;

    protected volatile String[] parts;
    protected volatile int partCount = -1;
    protected volatile boolean parsed = false;
    protected int version = UNKNOWN_VERSION;
    protected String encodedString;
    protected String e;
    protected String m;
    protected String d;
    protected String p;
    protected String q;
    protected String dp;
    protected String dq;
    protected String u;

    protected RSAPrivateKey rsaPrivateKeyFromPasspackJWK;
    protected RSAPublicKey rsaPublicKeyFromPasspackJWK;




    /**
     * Parse the encoded string into its parts
     */
    abstract void parse() ;

    protected String[] getParts() {
        return encodedString.split("\\|");
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

    protected void generateRsaKeyFromPasspackJWK() throws Exception{
        KeyFactory kf = KeyFactory.getInstance("RSA");

        BigInteger modulus = new BigInteger(1, Base64.getDecoder().decode(getM()));
        BigInteger publicExponent = new BigInteger(1, Base64.getDecoder().decode(getE()));
        if (parts.length >3) {
            BigInteger privateExponent = new BigInteger(1, Base64.getDecoder().decode(getD()));
            BigInteger primeP = new BigInteger(1, Base64.getDecoder().decode(getP()));
            BigInteger primeQ = new BigInteger(1, Base64.getDecoder().decode(getQ()));
            BigInteger primeExponentP = new BigInteger(1, Base64.getDecoder().decode(getDp()));
            BigInteger primeExponentQ = new BigInteger(1, Base64.getDecoder().decode(getDq()));
            BigInteger crtCoefficient = new BigInteger(1, Base64.getDecoder().decode(getU()));

            RSAKey rsaKey = null;
            RSAPrivateKeySpec privateKeySpec = new RSAMultiPrimePrivateCrtKeySpec(
                    modulus,
                    publicExponent,
                    privateExponent,
                    primeP,
                    primeQ,
                    primeExponentP,
                    primeExponentQ,
                    crtCoefficient,
                    null
            );
            rsaPrivateKeyFromPasspackJWK = (RSAPrivateKey) kf.generatePrivate(privateKeySpec);
        }
        // regenerate mod with the base64 encodeurl string

        String m = getM();
        m = m.replace("+", "-");
        m = m.replace("/", "_");
        m = m.replaceAll("=+$", "");

        modulus = new BigInteger(1, Base64.getDecoder().decode(getM()));
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
        rsaPublicKeyFromPasspackJWK = (RSAPublicKey) kf.generatePublic(publicKeySpec);

    }

}
