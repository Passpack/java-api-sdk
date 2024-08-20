package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.EncodedString;
import com.passpack.api.sdk.model.encryption.EncodedStringFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

public class AesEncryption_V3Test {

    @Test
    public void testRemovePadding() throws Exception {
        String paddedInput = "XoJ4RrE1rfMlyZDuOTZy0g==";
        AesEncryption_V3 aesEncryptionV3 = new AesEncryption_V3();
        String result = aesEncryptionV3.removePaddingFromString(paddedInput);
        Assertions.assertEquals("XoJ4RrE1rfMlyZDuOTZy0g", result);
    }

    @Test
    public void testDecryptEncodedStringProducedByApp() throws Exception {
        String input = "3#qeS86f/4oxkuTOFGo6gNAg==#awjsmBXotd6mJaWFgEgA4g==#jxoZ8dcXhI4tSCPLdw5CeeMejQc5pZMUXDcldbqo+mwIAfIknQeXUPY=";
        String password = "_24gceo182";
        EncodedString encodedString = EncodedStringFactory.createFromInputString(input, "#");
        switch (encodedString.getVersion()) {
            case 3:
                String plainTextDecryptedResult = AesEncryption.decryptFromPasspackString( password, encodedString);
                System.out.println("plainText: " + plainTextDecryptedResult);
                Assertions.assertEquals("personal notes", plainTextDecryptedResult);
                break;
            default:
                throw new Exception("Unsupported version");
        }
    }

    @Test
    public void testEncryptAndDecryptString() throws Exception {
        String input = UUID.randomUUID().toString();

        String password = UUID.randomUUID().toString();
        // Encrypt and encode the input
        EncodedString encodedString = AesEncryption_V3.encryptToPasspackString( password, input);
        System.out.println("encoded: " + encodedString.getCipherText());

        // Try to decrypt the cipher text
        String plainTextDecryptedResult = AesEncryption_V3.decryptFromPasspackString( password, encodedString);
        System.out.println("plainText: " + plainTextDecryptedResult);

        Assertions.assertEquals(input, plainTextDecryptedResult);
    }

    @Test
    public void testGetCharFromInputString() throws Exception {
        AesEncryption_V3 aesEncryptionV3 = new AesEncryption_V3();
        String input = "1234";
        Assertions.assertEquals('1', aesEncryptionV3.getCharFromInputString(input, 0));
        Assertions.assertEquals('2', aesEncryptionV3.getCharFromInputString(input, 1));
        Assertions.assertEquals('3', aesEncryptionV3.getCharFromInputString(input, 2));
        Assertions.assertEquals('4', aesEncryptionV3.getCharFromInputString(input, 3));
        Assertions.assertEquals('A', aesEncryptionV3.getCharFromInputString(input, 4));
    }

    @Test
    public void testEncryptLongString() throws Exception {
        int length = 300;
        Random random = new Random();
        String input = "";
        for (int i = 0; i < length; i++) {
            input += (char) (65+random.nextInt(26));
        }

        Assertions.assertEquals(length, input.length());
        String password = UUID.randomUUID().toString();

        // Encrypt and encode the input
        EncodedString encodedString = AesEncryption_V3.encryptToPasspackString( password, input);
        System.out.println("encoded: " + encodedString.getCipherText());

        // Try to decrypt the cipher text
        String plainTextDecryptedResult = AesEncryption_V3.decryptFromPasspackString( password, encodedString);
        System.out.println("plainText: " + plainTextDecryptedResult);

        Assertions.assertEquals(input, plainTextDecryptedResult);
    }


}
