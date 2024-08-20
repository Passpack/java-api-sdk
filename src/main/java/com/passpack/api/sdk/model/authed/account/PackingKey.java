package com.passpack.api.sdk.model.authed.account;

import com.passpack.api.sdk.model.encryption.EncodedString;
import lombok.Data;

@Data
public class PackingKey {
    private EncodedString encodedString;
    private String encodedePrivateKey;
    private String decodedPrivateKey;
    private String publicKey;
}
