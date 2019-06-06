package uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model;

import java.util.List;
import java.util.Map;

public class TransformationResult {

    public final Status status;
    public final List<String> warnings;
    public final List<String> errors;
    public final Map<String, Object> data;
    public final String caseTypeId;

    // region constructor
    public TransformationResult(
        Status status,
        List<String> warnings,
        List<String> errors,
        Map<String, Object> data,
        String caseTypeId
    ) {
        this.status = status;
        this.warnings = warnings;
        this.errors = errors;
        this.data = data;
        this.caseTypeId = caseTypeId;
    }
    // endregion
}
