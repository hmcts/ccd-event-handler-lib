package uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model;

import java.util.List;

public class TransformationResult {

    /**
     * Warnings that occurred during case mapping or validation.
     */
    public final List<String> warnings;

    /**
     * Errors that occurred during case mapping or validation.
     */
    public final List<String> errors;

    /**
     * Object representing the case that should be created in CCD.
     * It will be serialised to json. It can be a map or a domain object, as long as it serialises to expected json.
     */
    public final Object data;

    /**
     * CCD CaseTypeId that should be used when creating a case.
     */
    public final String caseTypeId;

    /**
     * CCD Event Id that should be used when creating a case.
     */
    public final String eventId;

    // region constructor
    public TransformationResult(
        List<String> warnings,
        List<String> errors,
        Object data,
        String caseTypeId,
        String eventId
    ) {
        this.warnings = warnings;
        this.errors = errors;
        this.data = data;
        this.caseTypeId = caseTypeId;
        this.eventId = eventId;
    }
    // endregion
}
