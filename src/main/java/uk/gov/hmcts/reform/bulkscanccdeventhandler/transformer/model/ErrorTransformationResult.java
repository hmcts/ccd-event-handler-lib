package uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model;

import java.util.List;

public class ErrorTransformationResult implements TransformationResult {

    /**
     * Warnings that occurred during case mapping or validation.
     */
    public final List<String> warnings;

    /**
     * Errors that occurred during case mapping or validation.
     */
    public final List<String> errors;

    public ErrorTransformationResult(List<String> warnings, List<String> errors) {
        this.warnings = warnings;
        this.errors = errors;
    }
}
