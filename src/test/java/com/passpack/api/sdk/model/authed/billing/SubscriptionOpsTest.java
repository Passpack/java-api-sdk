package com.passpack.api.sdk.model.authed.billing;

import com.passpack.api.model.publicApi.secure.account.billing.SubscriptionSummaryResponse;
import com.passpack.api.sdk.model.HttpResponseObject;
import com.passpack.api.sdk.model.authed.BaseAuthedTest;
import org.junit.jupiter.api.Test;

public class SubscriptionOpsTest extends BaseAuthedTest {

    /**
     * Test the retrieval of the subscription summary.
     * This is an independent login event and then a call to the API.
     *
     * Logs the subscription summary.
     * @throws Exception
     */
    @Test
    public void testRetrieveSubscriptionSummary() throws Exception {
        HttpResponseObject<SubscriptionSummaryResponse> response = SubscriptionOps.retrieveSubscriptionSummary();
        assertSuccessfulPasspackResponseObject(response);
    }
}
