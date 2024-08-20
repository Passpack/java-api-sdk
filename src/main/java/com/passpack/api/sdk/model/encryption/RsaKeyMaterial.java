package com.passpack.api.sdk.model.encryption;

public class RsaKeyMaterial extends RsaKeyMaterialBaseClass{

    public RsaKeyMaterial(String encodedString) {
        super();
        this.encodedString = encodedString;
        parse();
    }

   public void dumpKey() {
        System.out.println("original: " + encodedString);
        System.out.println("parts: " + partCount);
        for (int i = 0; i < partCount; i++) {
            System.out.println("Part " + i + ": " + parts[i]);
        }
   }

    void parse () {
        parts = getParts();
        partCount = parts.length;


        version = Integer.parseInt(parts[PART_VERSION]);
        m = parts[PART_M];
        e = parts[PART_E];

        if (partCount > 3) {
            d = parts[PART_D];
            p = parts[PART_P];
            q = parts[PART_Q];
            dp = parts[PART_DP];
            dq = parts[PART_DQ];
            u = parts[PART_U];  // also seen as QI
        }
        try {
            generateRsaKeyFromPasspackJWK();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating RSA private key from Passpack JWK");
        }
        parsed = true;
    }
}
