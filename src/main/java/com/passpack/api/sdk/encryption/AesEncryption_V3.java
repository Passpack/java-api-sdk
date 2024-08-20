package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.EncodedString;
import com.passpack.api.sdk.model.encryption.EncodedStringv3;

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

public class AesEncryption_V3 extends AesEncryptionBase {
    private static final String AES_GCM_ALGORITHM = "AES/GCM/NoPadding";
    private static final int V3_ITERATIONS = 0x3E8;
    private static final int V3_IV_LENGTH = 16;
    private static final int V3_SALT_LENGTH = 16;
    private static final int V3_KEY_LENGTH_IN_BITS = 256;
    private static final int V3_TAG_LENGTH_IN_BITS = 128;
    private static final int V3_PSON_HEADER_LENGTH = 11;
    private static final String V3_KEY_DERIVATION = "PBKDF2WithHmacSHA256";


    /**
     * Encrypts the input string using the password.  This is the version 3 encryption method.
     * @param password
     * @param toBeEncrypted
     * @return
     * @throws Exception
     */
    public static EncodedString encryptToPasspackString(String password, String toBeEncrypted) throws Exception {

        byte[] saltBytes = generateSaltBytes(V3_SALT_LENGTH);
        byte[] ivBytes = generateIvBytes(V3_IV_LENGTH);
        SecretKey secretKey = getKeyFromPasswordv3(password, saltBytes, V3_ITERATIONS, V3_KEY_LENGTH_IN_BITS);


        // Create the cipher instance and initialize for encryption.
        Cipher cipher = Cipher.getInstance(AES_GCM_ALGORITHM);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(V3_TAG_LENGTH_IN_BITS, ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);

        // Need to add the pson header to the input. The JS app uses a PSON object to be encrypted in v3.
        byte[] originalInputAsBytes = toBeEncrypted.getBytes();
        byte[] psonHacked = new byte[originalInputAsBytes.length + V3_PSON_HEADER_LENGTH];

        // copy the original into the hacked array starting at byte 12
        for (int i = 0; i < originalInputAsBytes.length; i++) {
            psonHacked[i + V3_PSON_HEADER_LENGTH] = originalInputAsBytes[i];
        }

        // Generate the PSON header for Passpack
        byte[] passpackPsonHeader = generatePSONHeaderForPasspack(toBeEncrypted.length());
        // copy the header into the hacked array
        for (int i = 0; i < passpackPsonHeader.length; i++) {
            psonHacked[i] = passpackPsonHeader[i];
        }
        // Do the encryption on the PSON array
        byte[] cipherTextWithPSON = cipher.doFinal(psonHacked);
        String cipherText = Base64.getEncoder()
                .encodeToString(cipherTextWithPSON);

        return EncodedStringv3.builder()
                .iv(Base64.getEncoder().encodeToString(ivBytes))
                .salt(Base64.getEncoder().encodeToString(saltBytes))
                .cipherText(cipherText)
                .build();
    }

    /**
     * Decrypts the data found in the @EncodedString object.  This is the version 3 decryption method.
     * @param password
     * @param encodedString
     * @return
     * @throws Exception
     */
    public static String decryptFromPasspackString(String password, EncodedString encodedString) throws Exception {

        Cipher cipher = Cipher.getInstance(AES_GCM_ALGORITHM);

        byte[] saltBytes = convertIntArrayToBytes(AesEncryption_V3.decodeInput(encodedString.getSalt()));
        byte[] ivBytes = convertIntArrayToBytes(AesEncryption_V3.decodeInput(encodedString.getIv()));
        SecretKey secretKey = getKeyFromPasswordv3(password, saltBytes, V3_ITERATIONS, V3_KEY_LENGTH_IN_BITS);

        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(V3_TAG_LENGTH_IN_BITS, ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(encodedString.getCipherText()));

        // Remove the first V3_PSON_HEADER_LENGTH bytes which are the PSON header
        // Now try to get the string which is byte 12 to the end
        byte[] subArray = new byte[plainText.length - V3_PSON_HEADER_LENGTH];
        for (int i = V3_PSON_HEADER_LENGTH; i < plainText.length; i++) {
            subArray[i - V3_PSON_HEADER_LENGTH] = plainText[i];
        }

        return new String(subArray);
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
    protected static SecretKey getKeyFromPasswordv3(String password, byte[] salt, int iterations, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance(V3_KEY_DERIVATION);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), AES_ALGORITHM);
        return secret;
    }

     /**
     * Generates the PSON header array to include with the encryption input for Passpack.  The app uses a PSON object
     * to be encrypted.  This is the method in pure Java to get past needing a non-existent PSON library.
     *
     * @param inputLength
     * @return
     */
    private static byte[] generatePSONHeaderForPasspack(int inputLength) {
        int[] header = new int[V3_PSON_HEADER_LENGTH];
        // Generates a header with some leading bits, "value" then an encoded length of the input
        header[0] = 246;
        header[1] = 1;
        header[2] = 252;
        header[3] = 5;
        header[4] = 118;
        header[5] = 97;
        header[6] = 108;
        header[7] = 117;
        header[8] = 101;
        header[9] = 252;
        header[10] = inputLength;

        // Create a byte array from the int array
        byte[] headerBytes = new byte[header.length];
        for (int i = 0; i < header.length; i++) {
            headerBytes[i] = (byte) header[i];
        }
        return headerBytes;
    }

}
