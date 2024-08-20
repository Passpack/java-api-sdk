package com.passpack.api.sdk.model.authed.organization;

import com.passpack.api.model.publicApi.paths.PortalUrlPaths;
import com.passpack.api.model.publicApi.secure.organization.RemoveOrganizationLicenseRequest;
import com.passpack.api.model.publicApi.secure.organization.RemoveOrganizationLicenseResponse;
import com.passpack.api.model.publicApi.secure.organization.RemoveVerifiedDomainMembershipRequest;
import com.passpack.api.model.publicApi.secure.organization.RemoveVerifiedDomainMembershipResponse;
import com.passpack.api.model.publicApi.secure.sharing.BulkUserPublicKeyRequest;
import com.passpack.api.model.publicApi.secure.sharing.BulkUserPublicKeyResponse;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.model.APIRequest;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.net.APIDriven;
import com.passpack.api.sdk.net.RequestMethod;

import java.util.Map;

public class OrganizationMembershipOps extends APIDriven {



    public static HttpResponseObject<RemoveVerifiedDomainMembershipResponse> removeVerifiedDomainMembership(RemoveVerifiedDomainMembershipRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.ORGANIZATION_REMOVE_VERIFIED_ACCOUNT_FROM_PLATFORM);
        RequestMethod method = RequestMethod.POST;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, RemoveVerifiedDomainMembershipResponse.class, ignoreResponse);
    }

    public static HttpResponseObject<RemoveOrganizationLicenseResponse> removeOrganizationLicense(RemoveOrganizationLicenseRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.ORGANIZATION_LICENSE_REMOVE);
        RequestMethod method = RequestMethod.POST;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, RemoveOrganizationLicenseResponse.class, ignoreResponse);
    }

    public static HttpResponseObject<BulkUserPublicKeyResponse> getUserPublicKeys(BulkUserPublicKeyRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.ORGANIZATION_PUBLIC_KEYS);
        RequestMethod method = RequestMethod.POST;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, BulkUserPublicKeyResponse.class, ignoreResponse);
    }
}
