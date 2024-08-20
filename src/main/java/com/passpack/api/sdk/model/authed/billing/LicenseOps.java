package com.passpack.api.sdk.model.authed.billing;

import com.passpack.api.model.publicApi.paths.PortalUrlPaths;
import com.passpack.api.model.publicApi.secure.account.billing.AddLicenseRequest;
import com.passpack.api.model.publicApi.secure.account.billing.AddLicenseResponse;
import com.passpack.api.model.publicApi.secure.organization.GetOrganizationLicensesRequest;
import com.passpack.api.model.publicApi.secure.organization.GetOrganizationLicensesResponse;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.model.APIRequest;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.net.APIDriven;
import com.passpack.api.sdk.net.RequestMethod;

import java.util.Map;

public class LicenseOps extends APIDriven {


    public static HttpResponseObject<GetOrganizationLicensesResponse> retrieveOrganizationLicenses(  GetOrganizationLicensesRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.BILLING_LICENSE_LIST);
        RequestMethod method = RequestMethod.POST;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, GetOrganizationLicensesResponse.class, ignoreResponse);
    }

    public static HttpResponseObject<AddLicenseResponse> addLicense(AddLicenseRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.BILLING_LICENSE_ADD);
        RequestMethod method = RequestMethod.POST;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, AddLicenseResponse.class, ignoreResponse);
    }


}
