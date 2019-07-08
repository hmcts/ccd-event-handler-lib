package uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils.sampledata;

import com.google.common.collect.ImmutableMap;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.ErrorTransformationResult;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.OkTransformationResult;

import java.util.List;

import static java.util.Collections.emptyList;

public final class SampleTransformationResult {

    public static OkTransformationResult okResult() {
        return warningResult(emptyList());
    }

    public static OkTransformationResult warningResult(List<String> warnings) {
        return new OkTransformationResult(
            warnings,
            ImmutableMap.of(
                "tr_key_1", "tr_value_1",
                "tr_key_2", "tr_value_2"
            ),
            "jurisdiction",
            "some_case_type_id",
            "some_event_id"
        );
    }

    public static ErrorTransformationResult errorResult(List<String> warnings, List<String> errors) {
        return new ErrorTransformationResult(warnings, errors);
    }

    private SampleTransformationResult() {
        // util class
    }
}
