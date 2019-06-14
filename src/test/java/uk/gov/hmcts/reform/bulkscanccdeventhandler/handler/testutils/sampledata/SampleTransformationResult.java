package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.testutils.sampledata;

import com.google.common.collect.ImmutableMap;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

import java.util.List;

import static java.util.Collections.emptyList;

public final class SampleTransformationResult {

    public static TransformationResult okResult() {
        return transformationResult(emptyList(), emptyList());
    }

    public static TransformationResult warningResult(List<String> warnings) {
        return transformationResult(warnings, emptyList());
    }

    public static TransformationResult errorResult(List<String> warnings, List<String> errors) {
        return transformationResult(warnings, errors);
    }

    public static TransformationResult transformationResult(
        List<String> warnings,
        List<String> errors
    ) {
        return new TransformationResult(
            warnings,
            errors,
            ImmutableMap.of(
                "tr_key_1", "tr_value_1",
                "tr_key_2", "tr_value_2"
            ),
            "jurisdiction",
            "some_case_type_id",
            "some_event_id"
        );
    }

    private SampleTransformationResult() {
        // util class
    }
}
