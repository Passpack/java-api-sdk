package com.passpack.api.sdk.model.authed.scim;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.passpack.api.model.publicApi.secure.password.*;
import com.passpack.api.model.publicApi.secure.scim.TeamListForSCIMResponse;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.model.authed.BaseAuthedTest;
import com.passpack.api.sdk.model.authed.team.TeamOps;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ScimOpsTest extends BaseAuthedTest {

    @Test
    public void testListScimTeams() throws Exception {
        HttpResponseObject<TeamListForSCIMResponse> response = ScimOps.retrievePasspackTeamListForSCIM();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
        assertSuccessfulPasspackResponseObject(response);
    }

}
