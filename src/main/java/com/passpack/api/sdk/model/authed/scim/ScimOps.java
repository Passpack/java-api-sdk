package com.passpack.api.sdk.model.authed.scim;

import com.passpack.api.model.publicApi.paths.PortalUrlPaths;
import com.passpack.api.model.publicApi.secure.organization.GetOrganizationLicensesRequest;
import com.passpack.api.model.publicApi.secure.password.*;
import com.passpack.api.model.publicApi.secure.scim.*;
import com.passpack.api.model.publicApi.secure.team.MultiManageTeamMembershipRequest;
import com.passpack.api.model.publicApi.secure.team.MultiManageTeamMembershipResponse;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.model.APIRequest;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.net.APIDriven;
import com.passpack.api.sdk.net.RequestMethod;

import java.util.Map;

public class ScimOps extends APIDriven {


    public static HttpResponseObject<TeamListForSCIMResponse> retrievePasspackTeamListForSCIM() {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.SCIM_TEAM_LIST);
        RequestMethod method = RequestMethod.GET;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, new TeamListForSCIMRequest(), TeamListForSCIMResponse.class, ignoreResponse);
    }



    public static HttpResponseObject<ScimMembersUpdateResponse> updateOrgMemberDetails(ScimMembersUpdateRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.SCIM_ORG_MEMBERS_UPDATE);
        RequestMethod method = RequestMethod.POST;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, ScimMembersUpdateResponse.class, ignoreResponse);
    }


    public static HttpResponseObject<ScimReportRunResponse> scimReportRun(ScimReportRunRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.SCIM_REPORT_RUN_PATH);
        RequestMethod method = RequestMethod.POST;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, ScimReportRunResponse.class, ignoreResponse);
    }



}
