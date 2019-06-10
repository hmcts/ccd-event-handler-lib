package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model;

import java.util.List;

public class CaseCreationResult {

    public final String caseId;
    public final List<String> errors;
    public final List<String> warnings;

    public CaseCreationResult(String caseId, List<String> errors, List<String> warnings) {
        this.caseId = caseId;
        this.errors = errors;
        this.warnings = warnings;
    }
}
