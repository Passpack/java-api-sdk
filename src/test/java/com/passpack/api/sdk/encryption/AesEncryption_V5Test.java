package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.EncodedString;
import com.passpack.api.sdk.model.encryption.EncodedStringFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

public class AesEncryption_V5Test {

    @Test
    public void testRemovePadding() throws Exception {
        String paddedInput = "XoJ4RrE1rfMlyZDuOTZy0g==";
        AesEncryption_V5 aesEncryptionV5 = new AesEncryption_V5();
        String result = aesEncryptionV5.removePaddingFromString(paddedInput);
        Assertions.assertEquals("XoJ4RrE1rfMlyZDuOTZy0g", result);
    }


    @Test
    public void testEncryptString() throws Exception {
        String data = "GWIMSRDWUPJEAMEWEIZXYLSRXSGIUHHORYXKEQDPFWKXGXQMWLGFVMYLTHCCEXUQMFMGAEGLHYPURBQVFHLJYOSLAVVWKTVZDJOMEQLVMVCUQTZNGNFISGXCTPMVFWQVDDJBVLLUBAXPLJAEAFJDPRPOFBFCOVENCTOMBFPJSUPZYAOPGIVZMMLNJXYWTLWEDMHJRBSAMLJPRERHJFHXFKCDUNPGAVEOSLAOCGQCZPVFCFDFYGURMZYZUZZPRDBAMHQMYZVOGZJVJHAAHZIXDAFKMSDIOCYNILWKGTPGCTYF";
        String password = "aef3e10e-f411-4c2e-bdc9-8489bf59e668";
        EncodedString encoded = AesEncryption_V5.encryptToPasspackString( password, data);
        System.out.println("encoded: " + encoded);
        System.out.println("input length: " + data.length());

    }

    @Test
    public void testDecryptEncodedStringProducedByApp() throws Exception {
        String expected = "GWIMSRDWUPJEAMEWEIZXYLSRXSGIUHHORYXKEQDPFWKXGXQMWLGFVMYLTHCCEXUQMFMGAEGLHYPURBQVFHLJYOSLAVVWKTVZDJOMEQLVMVCUQTZNGNFISGXCTPMVFWQVDDJBVLLUBAXPLJAEAFJDPRPOFBFCOVENCTOMBFPJSUPZYAOPGIVZMMLNJXYWTLWEDMHJRBSAMLJPRERHJFHXFKCDUNPGAVEOSLAOCGQCZPVFCFDFYGURMZYZUZZPRDBAMHQMYZVOGZJVJHAAHZIXDAFKMSDIOCYNILWKGTPGCTYF";
        String input = "5#101003#cpa9hKpwfNs1KmAbZN3zyzE/pE5OckzkMn/KL1nMTOU=#VPTLejra+21sBlref4Oym9prwrvT1CL1Ks+/y6cENK8=#uKAGUSE6OgInCDqvR5VOz6cCvXN5RWjW2jPOHCM7sU/GkiMUMeO5PuqyuV7h1sQ0P5mlCIRZnQLns1X/sdXcKcBNwbRdhOxXY643a2IowYcVjozH0NwMTcIeJVa6FoggQMUsZDnirJ4T2K+wanEWzSqkPRuJyWgsjxX67vGzcPYS3tQX6sIp9obghyIDZH/2cOk5hd60+Px2L6e4FgeAH9dM26tFhO1AWg2pGmh5MuQY299PPqv1OyjiHjmX2xA3J/2i4+n3YQa4WTe0+K2GjZlTUl6W8x4uvvZByrXZ0UvTkZh6AQxDJbY0UOoYlfpurngsHmQWQ+vd86WHXFpzwY2N4etYeUijjhxRxcbbAPv3SrQqU6DXgYSAvefJ9c62luf8Vr7qPJuXBAKfuKyI3sI6Et61yq5g96OCNjV6lu5afT2kYOFdwA==";
        String password = "aef3e10e-f411-4c2e-bdc9-8489bf59e668";
        EncodedString encodedString = EncodedStringFactory.createFromInputString(input, "#");
        switch (encodedString.getVersion()) {
            case 5:
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
        EncodedString encodedString = AesEncryption_V5.encryptToPasspackString( password, input);
        // Try to decrypt the cipher text
        String plainTextDecryptedResult = AesEncryption_V5.decryptFromPasspackString( password, encodedString);
        System.out.println("plainText: " + plainTextDecryptedResult);

        Assertions.assertEquals(input, plainTextDecryptedResult);
    }

    @Test
    public void testGetCharFromInputString() throws Exception {
        AesEncryption_V5 aesEncryptionV5 = new AesEncryption_V5();
        String input = "1234";
        Assertions.assertEquals('1', aesEncryptionV5.getCharFromInputString(input, 0));
        Assertions.assertEquals('2', aesEncryptionV5.getCharFromInputString(input, 1));
        Assertions.assertEquals('3', aesEncryptionV5.getCharFromInputString(input, 2));
        Assertions.assertEquals('4', aesEncryptionV5.getCharFromInputString(input, 3));
        Assertions.assertEquals('A', aesEncryptionV5.getCharFromInputString(input, 4));
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
        EncodedString encodedString = AesEncryption_V5.encryptToPasspackString( password, input);
        System.out.println("encoded ciphertext: " + encodedString.getCipherText());
        System.out.println("fully encoded: " + encodedString);

        // Try to decrypt the cipher text
        String plainTextDecryptedResult = AesEncryption_V5.decryptFromPasspackString( password, encodedString);
        System.out.println("plainText: " + plainTextDecryptedResult);

        System.out.println("password: " + password);
        System.out.println("input: " + input);

        Assertions.assertEquals(input, plainTextDecryptedResult);
    }


}
