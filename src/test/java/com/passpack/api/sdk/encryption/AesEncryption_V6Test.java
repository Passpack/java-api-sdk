package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.EncodedString;
import com.passpack.api.sdk.model.encryption.EncodedStringFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

public class AesEncryption_V6Test {

    @Test
    public void testRemovePadding() throws Exception {
        String paddedInput = "XoJ4RrE1rfMlyZDuOTZy0g==";
        AesEncryption_V6 aesEncryptionV6 = new AesEncryption_V6();
        String result = aesEncryptionV6.removePaddingFromString(paddedInput);
        Assertions.assertEquals("XoJ4RrE1rfMlyZDuOTZy0g", result);
    }


    @Test
    public void testEncryptString() throws Exception {
        String data = "GWIMSRDWUPJEAMEWEIZXYLSRXSGIUHHORYXKEQDPFWKXGXQMWLGFVMYLTHCCEXUQMFMGAEGLHYPURBQVFHLJYOSLAVVWKTVZDJOMEQLVMVCUQTZNGNFISGXCTPMVFWQVDDJBVLLUBAXPLJAEAFJDPRPOFBFCOVENCTOMBFPJSUPZYAOPGIVZMMLNJXYWTLWEDMHJRBSAMLJPRERHJFHXFKCDUNPGAVEOSLAOCGQCZPVFCFDFYGURMZYZUZZPRDBAMHQMYZVOGZJVJHAAHZIXDAFKMSDIOCYNILWKGTPGCTYF";
        String password = "aef3e10e-f411-4c2e-bdc9-8489bf59e668";
        EncodedString encoded = AesEncryption_V6.encryptToPasspackString( password, data);
        System.out.println("encoded: " + encoded);
        System.out.println("input length: " + data.length());

    }

    @Test
    public void testDecryptEncodedStringProducedByApp() throws Exception {
        String expected = "Hello, World!";
        String input = "6#101003#QbLny7qdCoqJoSG6edw1dUhZkIyC9HOkAW+d2L2WUdA=#LiSF3tDmUDBewUds/TSNEtYAZnrFM6TO7V85NAWMOPY=#F9xLrVucOcQdZ21olcZ8cQI+GJja6xrmBkO9BKA=";
        String password = "mySecretPassphrase";
        EncodedString encodedString = EncodedStringFactory.createFromInputString(input, "#");
        switch (encodedString.getVersion()) {
            case 6:
                String plainTextDecryptedResult = AesEncryption.decryptFromPasspackString( password, encodedString);
                System.out.println("plainDecrypted: " + plainTextDecryptedResult);
                System.out.println("input: " + expected);

                Assertions.assertEquals(expected, plainTextDecryptedResult);
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
        EncodedString encodedString = AesEncryption_V6.encryptToPasspackString( password, input);
        // Try to decrypt the cipher text
        String plainTextDecryptedResult = AesEncryption_V6.decryptFromPasspackString( password, encodedString);
        System.out.println("plainText: " + plainTextDecryptedResult);

        Assertions.assertEquals(input, plainTextDecryptedResult);
    }

    @Test
    public void testGetCharFromInputString() throws Exception {
        AesEncryption_V6 aesEncryptionV6 = new AesEncryption_V6();
        String input = "1234";
        Assertions.assertEquals('1', aesEncryptionV6.getCharFromInputString(input, 0));
        Assertions.assertEquals('2', aesEncryptionV6.getCharFromInputString(input, 1));
        Assertions.assertEquals('3', aesEncryptionV6.getCharFromInputString(input, 2));
        Assertions.assertEquals('4', aesEncryptionV6.getCharFromInputString(input, 3));
        Assertions.assertEquals('A', aesEncryptionV6.getCharFromInputString(input, 4));
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
        EncodedString encodedString = AesEncryption_V6.encryptToPasspackString( password, input);
        System.out.println("encoded ciphertext: " + encodedString.getCipherText());
        System.out.println("fully encoded: " + encodedString);

        // Try to decrypt the cipher text
        String plainTextDecryptedResult = AesEncryption_V6.decryptFromPasspackString( password, encodedString);
        System.out.println("plainText: " + plainTextDecryptedResult);

        System.out.println("password: " + password);
        System.out.println("input: " + input);

        Assertions.assertEquals(input, plainTextDecryptedResult);
    }


}
