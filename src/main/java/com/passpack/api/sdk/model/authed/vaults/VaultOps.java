package com.passpack.api.sdk.model.authed.vaults;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.passpack.api.model.publicApi.paths.PortalUrlPaths;
import com.passpack.api.model.publicApi.secure.organization.GetOrganizationLicensesRequest;
import com.passpack.api.model.publicApi.secure.password.*;
import com.passpack.api.model.publicApi.secure.passwordVault.ListPasswordVaultEntry;
import com.passpack.api.model.publicApi.secure.passwordVault.ListPasswordVaultRequest;
import com.passpack.api.model.publicApi.secure.passwordVault.ListPasswordVaultResponse;
import com.passpack.api.model.publicApi.secure.passwordVault.PasswordVaultDetail;
import com.passpack.api.model.publicApi.secure.team.MultiManageTeamMembershipRequest;
import com.passpack.api.model.publicApi.secure.team.MultiManageTeamMembershipResponse;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.encryption.AesEncryption;
import com.passpack.api.sdk.encryption.RsaEncryption;
import com.passpack.api.sdk.model.APIRequest;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.model.authed.account.PackingKey;
import com.passpack.api.sdk.model.encryption.EncodedString;
import com.passpack.api.sdk.model.encryption.EncodedStringFactory;
import com.passpack.api.sdk.model.encryption.RsaKeyMaterial;
import com.passpack.api.sdk.net.APIDriven;
import com.passpack.api.sdk.net.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VaultOps extends APIDriven {


    public static HttpResponseObject<ListPasswordVaultResponse> listVaults() {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_VAULT_LIST);
        RequestMethod method = RequestMethod.GET;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, new ListPasswordVaultRequest(), ListPasswordVaultResponse.class, ignoreResponse);
    }

    public static List<DecryptedPasswordVault> decryptVaults(List<ListPasswordVaultEntry> vaults, PackingKey decryptedPackingKey) {
        List<DecryptedPasswordVault> decodedVaults = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            if (null != vaults) {
                // Convert the decoded packing key into an RSA key material object
                RsaKeyMaterial rsaKeyMaterial = new RsaKeyMaterial(decryptedPackingKey.getDecodedPrivateKey());

                for (ListPasswordVaultEntry vault : vaults) {
                    DecryptedPasswordVault decryptedPasswordVault = new DecryptedPasswordVault();
                    decryptedPasswordVault.setListPasswordVaultEntry(vault);

                    /*
                    * Get to the encryption key
                     */
                    decryptedPasswordVault.setDecryptedVaultEncryptionKey(RsaEncryption.decryptWithPasspackJWK(rsaKeyMaterial, vault.getEncryptionKey()));
                    log.info ("Decrypted Vault Encryption Key: " + decryptedPasswordVault.getDecryptedVaultEncryptionKey());

                    // Decrypt the public and private keys for the vault
                    decryptedPasswordVault.setDecryptedPublicKey(AesEncryption.decryptFromPasspackString(decryptedPasswordVault.getDecryptedVaultEncryptionKey(), EncodedStringFactory.createFromInputString(vault.getPublicKey(), "#")).trim());
                    decryptedPasswordVault.setDecryptedPrivateKey(AesEncryption.decryptFromPasspackString(decryptedPasswordVault.getDecryptedVaultEncryptionKey(), EncodedStringFactory.createFromInputString(vault.getPrivateKey(), "#")).trim());
                    log.info ("Decrypted Vault Public Key: " + decryptedPasswordVault.getDecryptedPublicKey());
                    log.info ("Decrypted Vault Private Key: " + decryptedPasswordVault.getDecryptedPrivateKey());

                    /*
                    Get the data key
                     */
                    decryptedPasswordVault.setDecryptedDataKey(RsaEncryption.decryptWithPasspackJWK(rsaKeyMaterial, vault.getDataKey()));
                    log.info ("Decrypted Vault Data Key: " + decryptedPasswordVault.getDecryptedDataKey());
                    String decryptedDataKeyObject = AesEncryption.decryptFromPasspackString(decryptedPasswordVault.getDecryptedDataKey(), EncodedStringFactory.createFromInputString(vault.getData(), "#"));
                    log.info ("Decrypted Vault Data Key Object: " + decryptedDataKeyObject);
                    // Read the data key object into a PasswordVaultDetail object
                    PasswordVaultDetail passwordVaultDetail = objectMapper.readValue(decryptedDataKeyObject, PasswordVaultDetail.class);
                    decryptedPasswordVault.setPasswordVaultDetail(passwordVaultDetail);

                    // Add the decrypted vault to the list
                    decodedVaults.add(decryptedPasswordVault);
                }
            }
        } catch (Exception e) {
           log.error("Error decrypting vaults", e);
        }

        return decodedVaults;
    }



}
