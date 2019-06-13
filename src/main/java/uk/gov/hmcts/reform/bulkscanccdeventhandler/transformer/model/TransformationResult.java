package uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model;

import java.util.List;

public class TransformationResult {

    public final List<String> warnings;
    public final List<String> errors;
    public final Object data;
    public final String caseTypeId;
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
