package uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model;

import java.util.List;

public class ErrorTransformationResult extends TransformationResult {

    public ErrorTransformationResult(
        List<String> warnings,
        List<String> errors
    ) {
        super(warnings, errors, null, null, null, null);
    }
}
