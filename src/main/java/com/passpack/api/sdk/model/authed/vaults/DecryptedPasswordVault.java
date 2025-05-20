package com.passpack.api.sdk.model.authed.vaults;

import com.passpack.api.model.publicApi.secure.passwordVault.ListPasswordVaultEntry;
import com.passpack.api.model.publicApi.secure.passwordVault.PasswordVaultDetail;
import lombok.Data;

@Data
public class DecryptedPasswordVault {
    private PasswordVaultDetail passwordVaultDetail;
    private ListPasswordVaultEntry listPasswordVaultEntry;
    private String decryptedVaultEncryptionKey;
    private String decryptedPublicKey;
    private String decryptedPrivateKey;
    private String decryptedDataKey;
}
