package com.passpack.api.sdk.model.authed.organization;

import com.passpack.api.model.publicApi.secure.organization.*;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.model.authed.BaseAuthedTest;
import org.junit.jupiter.api.Test;

public class InvitationOpsTest extends BaseAuthedTest {

    @Test
    public void testInvite() throws Exception {
        InviteOrganizationTeamMembersRequest request = new InviteOrganizationTeamMembersRequest();
        request.getUsers().add(InviteOrganizationTeamMembersEntry.builder()
                .email("test@passpack.com")
                .firstName("Test")
                .lastName("User")
                .build());

        HttpResponseObject<InviteOrganizationTeamMembersResponse> response = InvitationOps.inviteOrganizationUser(request);
        assertSuccessfulPasspackResponseObject(response);
    }

    @Test
    public void testResendInvitation() throws Exception {
        ResendTeamInvitationRequest request = new ResendTeamInvitationRequest();
        request.getCids().add(755900l);
        HttpResponseObject<ResendTeamInvitationResponse> response = InvitationOps.resendTeamInvitation(request);
        assertSuccessfulPasspackResponseObject(response);
    }

    @Test
    public void testCancelInvitation() throws Exception {
        CancelTeamInvitationRequest request = new CancelTeamInvitationRequest();
        request.getCids().add(755900l);
        HttpResponseObject<CancelTeamInvitationResponse> response = InvitationOps.cancelTeamInvitation(request);
        assertSuccessfulPasspackResponseObject(response);
    }
}
