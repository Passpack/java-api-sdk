package com.passpack.api.sdk.model.encryption;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RsaKeyMaterialTest {

    @Test
    public void testDumpKey() {
        String passpackJWK = "2|k3c40XTtPsxtpqhHXYDuH53saGzCSeoGjAnV4FUKtaYvUyUKkHMMem6AMTlJ17t59xNM5eV6u84sPlMg1N0EgNaf+zWPabUeWL2v3FcnNWoqBERMD4PyNHWjbZRGwWz85Ymt/ybw+nZjLLkh3bE/K+1qbyH3RLdu8aIccLCANtc=|AQAB|M4XqUjSaV+CxRxNwq9jKWj2hRfMKBy0/UFp3YcQU09bWniRJBqNL7tMA8zHi/P9B4/PYYFLHiSrGBpig1f7K/XYeEvkRM/f4Wz5qd52Sq4j+lTa8K5Tt+Fd3YJCRH2P3cNxB7D9G50CfLlElkoas82EDC3EYkzJUpHLpjAH3gvE=|nO5K4W2mQC7cPGLgRuQKdprQuZw412YVa3rNl20htwICA5lAn2eop7rBccBbES/5lnHkKGpKrxHmfpe6dLeXAw==|8I9DtpTFnNJ4yODfnwFwczvc+KkPWCpkyXOv7leIn4Vx04y+seVTPl0fUXGz2/e9iAYnsdeFkk3lpVz3wPzenQ==|PcKKNa2xgBDxG9LN8RhOBd9nxaR1uk+ynln2D2IjoqJnqILnq9Rfy6Lz/pB1Ro5a65pm8IDkY4Hn9GpCNy0JgQ==|k+chtNJHpaHqTWelVffmkZMOy3v2STjXety8IIiFIb9EOtNgM7RRuBg9Ny/3a5koWMegBEIEh+2I8mD0mx1eKQ==|GMCXhSId6JVjrzERy8qVAo4ljX0u5Lbo47K8eGVrU+OMJ/HV+RAy4quOUkYRRDVLElHpEg96J/NgJnlkX65xyw==";
        RsaKeyMaterial rsaKeyMaterial = new RsaKeyMaterial(passpackJWK);
        rsaKeyMaterial.dumpKey();
        Assertions.assertEquals(9, rsaKeyMaterial.getParts().length);
        Assertions.assertEquals(2, rsaKeyMaterial.getVersion());
        Assertions.assertEquals("k3c40XTtPsxtpqhHXYDuH53saGzCSeoGjAnV4FUKtaYvUyUKkHMMem6AMTlJ17t59xNM5eV6u84sPlMg1N0EgNaf+zWPabUeWL2v3FcnNWoqBERMD4PyNHWjbZRGwWz85Ymt/ybw+nZjLLkh3bE/K+1qbyH3RLdu8aIccLCANtc=", rsaKeyMaterial.getM());
        Assertions.assertEquals("AQAB", rsaKeyMaterial.getE());
        Assertions.assertEquals("M4XqUjSaV+CxRxNwq9jKWj2hRfMKBy0/UFp3YcQU09bWniRJBqNL7tMA8zHi/P9B4/PYYFLHiSrGBpig1f7K/XYeEvkRM/f4Wz5qd52Sq4j+lTa8K5Tt+Fd3YJCRH2P3cNxB7D9G50CfLlElkoas82EDC3EYkzJUpHLpjAH3gvE=", rsaKeyMaterial.getD());
        Assertions.assertEquals("nO5K4W2mQC7cPGLgRuQKdprQuZw412YVa3rNl20htwICA5lAn2eop7rBccBbES/5lnHkKGpKrxHmfpe6dLeXAw==", rsaKeyMaterial.getP());
        Assertions.assertEquals("8I9DtpTFnNJ4yODfnwFwczvc+KkPWCpkyXOv7leIn4Vx04y+seVTPl0fUXGz2/e9iAYnsdeFkk3lpVz3wPzenQ==", rsaKeyMaterial.getQ());
        Assertions.assertEquals("PcKKNa2xgBDxG9LN8RhOBd9nxaR1uk+ynln2D2IjoqJnqILnq9Rfy6Lz/pB1Ro5a65pm8IDkY4Hn9GpCNy0JgQ==", rsaKeyMaterial.getDp());
        Assertions.assertEquals("k+chtNJHpaHqTWelVffmkZMOy3v2STjXety8IIiFIb9EOtNgM7RRuBg9Ny/3a5koWMegBEIEh+2I8mD0mx1eKQ==", rsaKeyMaterial.getDq());
        Assertions.assertEquals("GMCXhSId6JVjrzERy8qVAo4ljX0u5Lbo47K8eGVrU+OMJ/HV+RAy4quOUkYRRDVLElHpEg96J/NgJnlkX65xyw==", rsaKeyMaterial.getU());
    }


}
