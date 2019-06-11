package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.testutils.sampledata;

import com.google.common.collect.ImmutableMap;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecord;

public final class SampleCaseCreationRequest {

    public static CaseCreationRequest caseCreationRequest() {
        return caseCreationRequest(false);
    }

    public static CaseCreationRequest caseCreationRequest(boolean ignoreWarnings) {
        return new CaseCreationRequest(
            new ExceptionRecord(
                "id",
                "jurisdiction",
                "state",
                "case_type",
                ImmutableMap.of(
                    "er_key_1", "er_value_1",
                    "er_key_2", "er_value_2"
                )
            ),
            "event_id",
            ignoreWarnings
        );
    }

    private SampleCaseCreationRequest() {
        // util class
    }
}
