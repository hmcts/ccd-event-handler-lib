package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.testutils.sampledata;

import com.google.common.collect.ImmutableMap;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.Status;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

import java.util.List;

import static java.util.Collections.emptyList;

public final class SampleTransformationResult {

    public static TransformationResult okResult() {
        return transformationResult(Status.OK, emptyList(), emptyList());
    }

    public static TransformationResult warningResult(List<String> warnings) {
        return transformationResult(Status.WARNINGS, warnings, emptyList());
    }

    public static TransformationResult errorResult(List<String> warnings, List<String> errors) {
        return transformationResult(Status.ERRORS, warnings, errors);
    }

    public static TransformationResult transformationResult(
        Status status,
        List<String> warnings,
        List<String> errors
    ) {
        return new TransformationResult(
            status,
            warnings,
            errors,
            ImmutableMap.of(
                "tr_key_1", "tr_value_1",
                "tr_key_2", "tr_value_2"
            ),
            "some_case_type_id"
        );
    }

    private SampleTransformationResult() {
        // util class
    }
}
