package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.testutils.sampledata;

import com.google.common.collect.ImmutableMap;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecord;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecordRequest;

public final class SampleExceptionRecordRequest {

    public static ExceptionRecordRequest exceptionRecordRequest() {
        return exceptionRecordRequest(false);
    }

    public static ExceptionRecordRequest exceptionRecordRequest(boolean ignoreWarnings) {
        return new ExceptionRecordRequest(
            new ExceptionRecord(
                "id",
                "state",
                ImmutableMap.of(
                    "er_key_1", "er_value_1",
                    "er_key_2", "er_value_2"
                )
            ),
            "event_id",
            ignoreWarnings
        );
    }

    private SampleExceptionRecordRequest() {
        // util class
    }
}
