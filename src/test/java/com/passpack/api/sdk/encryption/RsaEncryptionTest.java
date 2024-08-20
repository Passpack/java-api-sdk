package com.passpack.api.sdk.encryption;

import com.passpack.api.sdk.model.encryption.RsaKeyMaterial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Base64;

public class RsaEncryptionTest {

    @Test
    public void testCanCreatePublicKey() throws Exception {
        String publicKey = "2|uSxgWHftIitMHtCmzpBJqfrd6o7akky0yIm5vFbgUFPu9kLdf+vGXx4cS6d6RqCABtFBOSR2Hk3ebrZynCBIxlJigDStYFOt83WXxIrl4LpwI56wrPDTpnoTNoJMAwI9D0qihcDKXeXz8I9E10qsYCLXG0M1QERfhWK22u1MRa8|AQAB";
        RsaKeyMaterial rsaKeyMaterial = new RsaKeyMaterial(publicKey);
    // Should not throw exception
        rsaKeyMaterial.dumpKey();
    }

    @Test
    public void testDecryptionV2() throws Exception {
        String encodedData = "FDj0ZhEiOAU9TJ7+T2H7THTZ1cUvrNGlkh9QL+eAXZATSAnBqBkSMN5i8iDDgqIs64IKAYl59Ppgu36CWFOMsAhgxCcuBw55CrGK3eUaqZDg/UU6/S0kbfiVaxdcSWqzcyZ2gTaWUlTBpgVDyCEAqzlPNpUqB5Q8FnEFUSBT1ks=";
        String encodedKey = "2|k3c40XTtPsxtpqhHXYDuH53saGzCSeoGjAnV4FUKtaYvUyUKkHMMem6AMTlJ17t59xNM5eV6u84sPlMg1N0EgNaf+zWPabUeWL2v3FcnNWoqBERMD4PyNHWjbZRGwWz85Ymt/ybw+nZjLLkh3bE/K+1qbyH3RLdu8aIccLCANtc=|AQAB|M4XqUjSaV+CxRxNwq9jKWj2hRfMKBy0/UFp3YcQU09bWniRJBqNL7tMA8zHi/P9B4/PYYFLHiSrGBpig1f7K/XYeEvkRM/f4Wz5qd52Sq4j+lTa8K5Tt+Fd3YJCRH2P3cNxB7D9G50CfLlElkoas82EDC3EYkzJUpHLpjAH3gvE=|nO5K4W2mQC7cPGLgRuQKdprQuZw412YVa3rNl20htwICA5lAn2eop7rBccBbES/5lnHkKGpKrxHmfpe6dLeXAw==|8I9DtpTFnNJ4yODfnwFwczvc+KkPWCpkyXOv7leIn4Vx04y+seVTPl0fUXGz2/e9iAYnsdeFkk3lpVz3wPzenQ==|PcKKNa2xgBDxG9LN8RhOBd9nxaR1uk+ynln2D2IjoqJnqILnq9Rfy6Lz/pB1Ro5a65pm8IDkY4Hn9GpCNy0JgQ==|k+chtNJHpaHqTWelVffmkZMOy3v2STjXety8IIiFIb9EOtNgM7RRuBg9Ny/3a5koWMegBEIEh+2I8mD0mx1eKQ==|GMCXhSId6JVjrzERy8qVAo4ljX0u5Lbo47K8eGVrU+OMJ/HV+RAy4quOUkYRRDVLElHpEg96J/NgJnlkX65xyw==";
        RsaKeyMaterial rsaKeyMaterial = new RsaKeyMaterial(encodedKey);
        String decodedData = RsaEncryption.decryptWithPasspackJWK(rsaKeyMaterial, encodedData);
        Assertions.assertEquals("_u25w7y3kc", decodedData);
    }

    @Test
    public void testEncryptAndDecryptV2() throws  Exception {
        String passpackJWK = "2|k3c40XTtPsxtpqhHXYDuH53saGzCSeoGjAnV4FUKtaYvUyUKkHMMem6AMTlJ17t59xNM5eV6u84sPlMg1N0EgNaf+zWPabUeWL2v3FcnNWoqBERMD4PyNHWjbZRGwWz85Ymt/ybw+nZjLLkh3bE/K+1qbyH3RLdu8aIccLCANtc=|AQAB|M4XqUjSaV+CxRxNwq9jKWj2hRfMKBy0/UFp3YcQU09bWniRJBqNL7tMA8zHi/P9B4/PYYFLHiSrGBpig1f7K/XYeEvkRM/f4Wz5qd52Sq4j+lTa8K5Tt+Fd3YJCRH2P3cNxB7D9G50CfLlElkoas82EDC3EYkzJUpHLpjAH3gvE=|nO5K4W2mQC7cPGLgRuQKdprQuZw412YVa3rNl20htwICA5lAn2eop7rBccBbES/5lnHkKGpKrxHmfpe6dLeXAw==|8I9DtpTFnNJ4yODfnwFwczvc+KkPWCpkyXOv7leIn4Vx04y+seVTPl0fUXGz2/e9iAYnsdeFkk3lpVz3wPzenQ==|PcKKNa2xgBDxG9LN8RhOBd9nxaR1uk+ynln2D2IjoqJnqILnq9Rfy6Lz/pB1Ro5a65pm8IDkY4Hn9GpCNy0JgQ==|k+chtNJHpaHqTWelVffmkZMOy3v2STjXety8IIiFIb9EOtNgM7RRuBg9Ny/3a5koWMegBEIEh+2I8mD0mx1eKQ==|GMCXhSId6JVjrzERy8qVAo4ljX0u5Lbo47K8eGVrU+OMJ/HV+RAy4quOUkYRRDVLElHpEg96J/NgJnlkX65xyw==";
        RsaKeyMaterial rsaKeyMaterial = new RsaKeyMaterial(passpackJWK);

        String dataToEncode = "Hello World!";
        String encodedData = RsaEncryption.encryptWithPasspackJWK(rsaKeyMaterial, dataToEncode);
        System.out.println("encoded: " + encodedData);
        String decodedData = RsaEncryption.decryptWithPasspackJWK(rsaKeyMaterial, encodedData);
        Assertions.assertEquals(dataToEncode, decodedData);

    }

    @Test
    public void testConvertKeyPart() throws Exception {
        BigInteger v = new BigInteger(1, Base64.getUrlDecoder().decode("AQAB"));
        System.out.println("v: " + v);

    }
}
