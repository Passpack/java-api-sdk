package com.passpack.api.sdk.model.authed.team;

import com.passpack.api.model.publicApi.paths.PortalUrlPaths;
import com.passpack.api.model.publicApi.secure.organization.GetOrganizationLicensesRequest;
import com.passpack.api.model.publicApi.secure.password.*;
import com.passpack.api.model.publicApi.secure.team.*;
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
        Map<String, Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, new GetOrganizationLicensesRequest(), PasswordGroupListResponse.class, ignoreResponse);
    }

    @Deprecated
    public static HttpResponseObject<GroupDetailResponse> groupDetail(GroupDetailRequest request) {
        return teamDetail(request);
    }

    public static HttpResponseObject<GroupDetailResponse> teamDetail(GroupDetailRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_DETAIL);
        RequestMethod method = RequestMethod.POST;
        Map<String, Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, GroupDetailResponse.class, ignoreResponse);
    }

    @Deprecated
    public static HttpResponseObject<RemoveConnectionFromPasswordGroupResponse> removeGroupMember(RemoveConnectionFromPasswordGroupRequest request) {
        return removeTeamMember(request);
    }

    public static HttpResponseObject<RemoveConnectionFromPasswordGroupResponse> removeTeamMember(RemoveConnectionFromPasswordGroupRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_REMOVE_MEMBER);
        RequestMethod method = RequestMethod.POST;
        Map<String, Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, RemoveConnectionFromPasswordGroupResponse.class, ignoreResponse);
    }



    public static HttpResponseObject<CreateTeamResponse> createTeam(CreateTeamRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_CREATE);
        RequestMethod method = RequestMethod.PUT;
        Map<String, Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, CreateTeamResponse.class, ignoreResponse);
    }

    @Deprecated
    public static HttpResponseObject<DeleteGroupResponse> deleteGroup(DeleteGroupRequest request) {
        return deleteTeam(request);
    }

    public static HttpResponseObject<DeleteGroupResponse> deleteTeam(DeleteGroupRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_DELETE);
        RequestMethod method = RequestMethod.DELETE;
        Map<String, Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, DeleteGroupResponse.class, ignoreResponse);
    }

    public static HttpResponseObject<UpdateTeamResponse> updateTeam(UpdateTeamRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_UPDATE);
        RequestMethod method = RequestMethod.POST;
        Map<String, Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, UpdateTeamResponse.class, ignoreResponse);
    }

    public static HttpResponseObject<UpdateTeamParentResponse> updateTeamParent(UpdateTeamParentRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_UPDATE_PARENT);
        RequestMethod method = RequestMethod.POST;
        Map<String, Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, UpdateTeamParentResponse.class, ignoreResponse);
    }

    public static HttpResponseObject<MultiManageTeamMembershipResponse> manageTeamMembership(MultiManageTeamMembershipRequest request) {
        RequestOptions options = getDefaultOptions(PortalUrlPaths.PASSWORD_GROUP_MANAGE_MEMBERSHIP);
        RequestMethod method = RequestMethod.POST;
        Map<String, Object> queryParams = null;
        APIRequest apiRequest = new APIRequest(options, method, queryParams);
        boolean ignoreResponse = false;
        return sendRequest(apiRequest, request, MultiManageTeamMembershipResponse.class, ignoreResponse);
    }


}
