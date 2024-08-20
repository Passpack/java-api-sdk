package com.passpack.api.sdk.model.authed.team;

import com.passpack.api.model.publicApi.secure.password.*;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.model.authed.BaseAuthedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TeamOpsTest extends BaseAuthedTest {

    @Test
    public void testListTeams() throws Exception {
        HttpResponseObject<PasswordGroupListResponse> response = TeamOps.list();
        assertSuccessfulPasspackResponseObject(response);
    }

    @Test
    public void getGroupDetail() throws Exception {
        GroupDetailRequest request = GroupDetailRequest.builder().id(36235).build();
        HttpResponseObject<GroupDetailResponse> response = TeamOps.groupDetail(request);
        assertSuccessfulPasspackResponseObject(response);
    }

    @Test
    public void removeGroupMemberTest() throws Exception {
        RemoveConnectionFromPasswordGroupRequest request = RemoveConnectionFromPasswordGroupRequest.builder().groupIds(Arrays.asList(36235l)).otherId(33016).build();
        HttpResponseObject<RemoveConnectionFromPasswordGroupResponse> response = TeamOps.removeGroupMember(request);
        assertSuccessfulPasspackResponseObject(response);
    }

    @Test
    public void deletePasswordGroup() throws Exception {
        DeleteGroupRequest request = DeleteGroupRequest.builder().id(763507).build();
        HttpResponseObject<DeleteGroupResponse> response = TeamOps.deleteGroup(request);
        assertSuccessfulPasspackResponseObject(response);
    }
}
