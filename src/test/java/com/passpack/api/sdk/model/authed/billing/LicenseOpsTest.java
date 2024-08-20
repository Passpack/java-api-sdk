package com.passpack.api.sdk.model.authed.billing;

import com.passpack.api.model.publicApi.secure.account.billing.AddLicenseRequest;
import com.passpack.api.model.publicApi.secure.account.billing.AddLicenseResponse;
import com.passpack.api.model.publicApi.secure.organization.GetOrganizationLicensesRequest;
import com.passpack.api.model.publicApi.secure.organization.GetOrganizationLicensesResponse;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.model.authed.BaseAuthedTest;
import org.junit.jupiter.api.Test;

public class LicenseOpsTest extends BaseAuthedTest {

    /**
     * Test the retrieval of the licenses.
     * This is an independent login event and then a call to the API.
     *
     * Logs the license list.
     * @throws Exception
     */
    @Test
    public void testRetrieveLicenses() throws Exception {
        HttpResponseObject<GetOrganizationLicensesResponse> response = LicenseOps.retrieveOrganizationLicenses(GetOrganizationLicensesRequest.builder()
                .includeNonLicensedUsers(true).build());
        assertSuccessfulPasspackResponseObject(response);
    }

    /**
     * Test the addition of a license
     * This is an independent login event and then a call to the API.
     *
     * Logs the summary.
     * @throws Exception
     */
    @Test
    public void testAddLicense() throws Exception {
        HttpResponseObject<AddLicenseResponse> response = LicenseOps.addLicense(AddLicenseRequest.builder().requestedLicenses(1).build());
        assertSuccessfulPasspackResponseObject(response);
    }

}
