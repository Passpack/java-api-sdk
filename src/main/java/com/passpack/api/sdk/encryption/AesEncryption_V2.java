package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.EncodedString;
import com.passpack.api.sdk.model.encryption.EncodedStringv2;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AesEncryption_V2 extends AesEncryptionBase {
    private static final String AES_CBC_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int V2_IV_LENGTH = 16;
    private static final int V2_SALT_LENGTH = 16;
    private static final int V2_ITERATIONS = 0x3E8;
    private static final int V2_KEY_LENGTH_IN_BITS = 128;
    private static final String V2_KEY_DERIVATION = "PBKDF2WithHmacSHA256";

    /**
     * Encrypts the input string using the password.  This is the version 3 encryption method.
     *
     * @param password
     * @param toBeEncrypted
     * @return
     * @throws Exception
     */
    public static EncodedString encryptToPasspackString(String password, String toBeEncrypted) throws Exception {

        byte[] saltBytes = generateSaltBytes(V2_SALT_LENGTH);
        byte[] ivBytes = generateIvBytes(V2_IV_LENGTH);
        SecretKey secretKey = getKeyFromPasswordv2(password, saltBytes, V2_ITERATIONS, V2_KEY_LENGTH_IN_BITS);


        // Create the cipher instance and initialize for encryption.
        Cipher cipher = Cipher.getInstance(AES_CBC_ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        byte[] originalInputAsBytes = toBeEncrypted.getBytes();
        byte[] cipherTextArray = cipher.doFinal(originalInputAsBytes);
        String cipherText = Base64.getEncoder()
                .encodeToString(cipherTextArray);

        return EncodedStringv2.builder()
                .iv(Base64.getEncoder().encodeToString(ivBytes))
                .salt(Base64.getEncoder().encodeToString(saltBytes))
                .cipherText(cipherText)
                .build();    }

    /**
     * Decrypts the data found in the @EncodedString object.  This is the version 2 decryption method.
     *
     * @param password
     * @param encodedString
     * @return
     * @throws Exception
     */
    public static String decryptFromPasspackString(String password, EncodedString encodedString) throws Exception {

        Cipher cipher = Cipher.getInstance(AES_CBC_ALGORITHM);

        byte[] saltBytes = convertIntArrayToBytes(AesEncryption_V2.decodeInput(encodedString.getSalt()));
        byte[] ivBytes = convertIntArrayToBytes(AesEncryption_V2.decodeInput(encodedString.getIv()));
        SecretKey secretKey = getKeyFromPasswordv2(password, saltBytes, V2_ITERATIONS, V2_KEY_LENGTH_IN_BITS);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(encodedString.getCipherText()));
        return new String(plainText);
    }


    /**
     * @param password
     * @param salt
     * @param iterations
     * @param keyLength
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    protected static SecretKey getKeyFromPasswordv2(String password, byte[] salt, int iterations, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance(V2_KEY_DERIVATION);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), AES_ALGORITHM);
        return secret;
    }


}
