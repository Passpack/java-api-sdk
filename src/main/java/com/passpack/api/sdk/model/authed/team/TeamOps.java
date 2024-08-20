package com.passpack.api.sdk.model.authed.team;

import com.passpack.api.model.publicApi.paths.PortalUrlPaths;
import com.passpack.api.model.publicApi.secure.organization.GetOrganizationLicensesRequest;
import com.passpack.api.model.publicApi.secure.password.*;
import com.passpack.api.model.publicApi.secure.team.MultiManageTeamMembershipRequest;
import com.passpack.api.model.publicApi.secure.team.MultiManageTeamMembershipResponse;
import com.passpack.api.sdk.RequestOptions;
import com.passpack.api.sdk.model.APIRequest;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.net.APIDriven;
import com.passpack.api.sdk.net.RequestMethod;

import java.util.Map;

public class TeamOps extends APIDriven {


    public static HttpResponseObject<PasswordGroupListResponse> list() {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_LIST);
        RequestMethod method = RequestMethod.GET;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, new GetOrganizationLicensesRequest(), PasswordGroupListResponse.class, ignoreResponse);
    }

    public static HttpResponseObject<GroupDetailResponse> groupDetail(GroupDetailRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_DETAIL);
        RequestMethod method = RequestMethod.POST;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, GroupDetailResponse.class, ignoreResponse);
    }


    public static HttpResponseObject<RemoveConnectionFromPasswordGroupResponse> removeGroupMember(RemoveConnectionFromPasswordGroupRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_REMOVE_MEMBER);
        RequestMethod method = RequestMethod.POST;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, RemoveConnectionFromPasswordGroupResponse.class, ignoreResponse);
    }

    public static HttpResponseObject<CreateGroupResponse> createGroup(CreateGroupRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_CREATE);
        RequestMethod method = RequestMethod.PUT;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, CreateGroupResponse.class, ignoreResponse);
    }

    public static HttpResponseObject<DeleteGroupResponse> deleteGroup(DeleteGroupRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_DELETE);
        RequestMethod method = RequestMethod.DELETE;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, DeleteGroupResponse.class, ignoreResponse);
    }

    public static HttpResponseObject<MultiManageTeamMembershipResponse> manageTeamMembership(MultiManageTeamMembershipRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_MANAGE_MEMBERSHIP);
        RequestMethod method = RequestMethod.POST;
        Map<String,Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, MultiManageTeamMembershipResponse.class, ignoreResponse);
    }


}
