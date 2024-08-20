package com.passpack.api.sdk.model.authed.organization;

import com.passpack.api.model.publicApi.secure.organization.RemoveOrganizationLicenseRequest;
import com.passpack.api.model.publicApi.secure.organization.RemoveOrganizationLicenseResponse;
import com.passpack.api.model.publicApi.secure.organization.RemoveVerifiedDomainMembershipRequest;
import com.passpack.api.model.publicApi.secure.organization.RemoveVerifiedDomainMembershipResponse;
import com.passpack.api.model.publicApi.secure.sharing.BulkUserPublicKeyRequest;
import com.passpack.api.model.publicApi.secure.sharing.BulkUserPublicKeyResponse;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.model.authed.BaseAuthedTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class OrganizationMembershipOpsTest extends BaseAuthedTest {

    @Test
    public void testRemoveOrganizationMembership() throws Exception {
        RemoveVerifiedDomainMembershipRequest request = new RemoveVerifiedDomainMembershipRequest();
        request.getCustomerIds().add(195106l);
        request.setInApp(true);
        HttpResponseObject<RemoveVerifiedDomainMembershipResponse> response = OrganizationMembershipOps.removeVerifiedDomainMembership(request);
        assertSuccessfulPasspackResponseObject(response);
    }

    @Test
    public void testRemoveLicense() throws Exception {
        RemoveOrganizationLicenseRequest request = new RemoveOrganizationLicenseRequest();
        request.getCustomerIds().add(755500l);
        HttpResponseObject<RemoveOrganizationLicenseResponse> response = OrganizationMembershipOps.removeOrganizationLicense(request);
        assertSuccessfulPasspackResponseObject(response);
    }

    @Test
    public void testRetrievePublicKeys() throws Exception {
        BulkUserPublicKeyRequest request = new BulkUserPublicKeyRequest();
        request.getConnectionIds().addAll(Arrays.asList(771100l, 206021l));
        HttpResponseObject<BulkUserPublicKeyResponse> response = OrganizationMembershipOps.getUserPublicKeys(request);
        assertSuccessfulPasspackResponseObject(response);
        Assertions.assertEquals(2, response.getReturnObject().getPublicKeys().size());
    }
}
