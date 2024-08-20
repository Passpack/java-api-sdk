package com.passpack.api.sdk.model.authed.billing;

import com.passpack.api.model.publicApi.paths.PortalUrlPaths;
import com.passpack.api.model.publicApi.secure.account.billing.SubscriptionSummaryResponse;
import com.passpack.api.model.publicApi.secure.organization.GetOrganizationLicensesRequest;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.model.APIRequest;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.net.APIDriven;
import com.passpack.api.sdk.net.RequestMethod;

import java.util.Map;

public class SubscriptionOps extends APIDriven {


    public static HttpResponseObject<SubscriptionSummaryResponse> retrieveSubscriptionSummary() {

        RequestOptions options = getDefaultOptions(PortalUrlPaths.BILLING_SUBSCRIPTION_SUMMARY);
        RequestMethod method = RequestMethod.GET;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, new GetOrganizationLicensesRequest(), SubscriptionSummaryResponse.class, ignoreResponse);
    }
}
