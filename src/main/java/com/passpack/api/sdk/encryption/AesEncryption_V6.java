package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.EncodedString;
import com.passpack.api.sdk.model.encryption.EncodedStringv6;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AesEncryption_V6 extends AesEncryptionBase {
    private static final String AES_GCM_ALGORITHM = "AES/GCM/NoPadding";
    private static final int V6_ITERATIONS = 101003;
    public static final int V6_ITERATIONS_DEFAULT = V6_ITERATIONS;
    private static final int V6_IV_LENGTH = 32;
    private static final int V6_SALT_LENGTH = 32;
    private static final int V6_KEY_LENGTH_IN_BITS = 256;
    private static final int V6_TAG_LENGTH_IN_BITS = 128;
    private static final String V6_KEY_DERIVATION = "PBKDF2WithHmacSHA256";


    /**
     * Encrypts the input string using the password.  This is the version 6 encryption method.
     * The main difference is no more use of PSON encoding.
     * @param password
     * @param toBeEncrypted
     * @return
     * @throws Exception
     */
    public static EncodedString encryptToPasspackString(String password, String toBeEncrypted) throws Exception {

        byte[] saltBytes = generateSaltBytes(V6_SALT_LENGTH);
        byte[] ivBytes = generateIvBytes(V6_IV_LENGTH);
        int iterations = V6_ITERATIONS;
        SecretKey secretKey = getKeyFromPasswordV6(password, saltBytes, iterations, V6_KEY_LENGTH_IN_BITS);

        // Create the cipher instance and initialize for encryption.
        Cipher cipher = Cipher.getInstance(AES_GCM_ALGORITHM);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(V6_TAG_LENGTH_IN_BITS, ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);

        // The input is not wrapped in PSON for version 6.
        byte[] toBeEncryptedBytes = toBeEncrypted.getBytes();
        byte[] cipherTextBytes = cipher.doFinal(toBeEncryptedBytes);
        String cipherText = Base64.getEncoder()
                .encodeToString(cipherTextBytes);

        EncodedString rv =  EncodedStringv6.builder()
                .iterations(iterations)
                .iv(Base64.getEncoder().encodeToString(ivBytes))
                .salt(Base64.getEncoder().encodeToString(saltBytes))
                .cipherText(cipherText)
                .build();
        return rv;
    }

    /**
     * Decrypts the data found in the @EncodedString object.
     * @param password
     * @param encodedString
     * @return
     * @throws Exception
     */
    public static String decryptFromPasspackString(String password, EncodedString encodedString) throws Exception {

        Cipher cipher = Cipher.getInstance(AES_GCM_ALGORITHM);
        byte[] saltBytes = convertIntArrayToBytes(AesEncryption_V6.decodeInput(encodedString.getSalt()));
        byte[] ivBytes = convertIntArrayToBytes(AesEncryption_V6.decodeInput(encodedString.getIv()));
        int iterations = encodedString.getIterations();
        SecretKey secretKey = getKeyFromPasswordV6(password, saltBytes, iterations, V6_KEY_LENGTH_IN_BITS);

        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(V6_TAG_LENGTH_IN_BITS, ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(encodedString.getCipherText()));

        // Version 6 produces a plain text - no wrapper.
        return new String(plainText).trim();
    }



    /**
     *
     * @param password
     * @param salt
     * @param iterations
     * @param keyLength
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    protected static SecretKey getKeyFromPasswordV6(String password, byte[] salt, int iterations, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance(V6_KEY_DERIVATION);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), AES_ALGORITHM);
        return secret;
    }


}
