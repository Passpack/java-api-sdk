package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.EncodedString;
import com.passpack.api.sdk.model.encryption.EncodedStringFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class AesEncryption_V2Test {

    @Test
    public void testDecryptEncodedStringProducedByApp() throws Exception {
        String input = "2#Lxto7hH+B7amafwLaGcztQ==#x3/w0b//rXJRYQ5PasbqwA==#UOpRO/Hf5lN+/cbD6lWoxiZN5OmbUZVFsxCQJ+Q4tyqawp2Rb9rbmtg8pR9nfKCm";
        String password = "acdf8cd7-8287-408b-89e4-a086b6d0ae60";
        EncodedString encodedString = EncodedStringFactory.createFromInputString(input, "#");
        switch (encodedString.getVersion()) {
            case 2:
                String plainTextDecryptedResult = AesEncryption.decryptFromPasspackString( password, encodedString);
                System.out.println("plainText: " + plainTextDecryptedResult);
                Assertions.assertEquals("49c47977-1de4-4fff-abdc-5d072089e3e0", plainTextDecryptedResult);
                break;
            default:
                throw new Exception("Unsupported version");
        }
    }

    @Test
    public void testEncryptAndDecryptString() throws Exception {
        String input = UUID.randomUUID().toString();

        String password = UUID.randomUUID().toString();
        System.out.println("Random password: " + password);
        // Encrypt and encode the input
        EncodedString encodedString = AesEncryption_V2.encryptToPasspackString( password, input);
        System.out.println("encoded: " + encodedString.getCipherText());
        System.out.println("encoded: " + encodedString.toString());

        // Try to decrypt the cipher text
        String plainTextDecryptedResult = AesEncryption_V2.decryptFromPasspackString( password, encodedString);
        System.out.println("plainText: " + plainTextDecryptedResult);

        Assertions.assertEquals(input, plainTextDecryptedResult);
    }


}
