package com.passpack.api.sdk.model.authed.organization;

import com.passpack.api.model.publicApi.PasspackResponseObject;
import com.passpack.api.model.publicApi.paths.PortalUrlPaths;
import com.passpack.api.model.publicApi.secure.organization.*;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.model.APIRequest;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.net.APIDriven;
import com.passpack.api.sdk.net.RequestMethod;

import java.util.Map;

public class InvitationOps extends APIDriven {
    public static  HttpResponseObject<InviteOrganizationTeamMembersResponse> inviteOrganizationUser(InviteOrganizationTeamMembersRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.ORGANIZATION_INVITE_USERS);
        return sendInvitationRequest(options,  request, InviteOrganizationTeamMembersResponse.class);
    }

    public static  HttpResponseObject<ResendTeamInvitationResponse> resendTeamInvitation(ResendTeamInvitationRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.ORGANIZATION_INVITE_RESEND);
        return sendInvitationRequest(options,  request, ResendTeamInvitationResponse.class);
    }

    public static HttpResponseObject<CancelTeamInvitationResponse> cancelTeamInvitation(CancelTeamInvitationRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.ORGANIZATION_INVITE_CANCEL);
        return sendInvitationRequest(options,  request, CancelTeamInvitationResponse.class);
    }

    private static HttpResponseObject  sendInvitationRequest(RequestOptions options, Object request, Class responseClass) {
        RequestMethod method = RequestMethod.POST;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, responseClass, ignoreResponse);
    }
}
