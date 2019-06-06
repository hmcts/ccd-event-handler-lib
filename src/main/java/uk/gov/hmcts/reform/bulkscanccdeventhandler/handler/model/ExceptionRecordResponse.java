package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model;

import java.util.List;

public class ExceptionRecordResponse {

    public final List<String> errors;
    public final List<String> warnings;

    public ExceptionRecordResponse(List<String> errors, List<String> warnings) {
        this.errors = errors;
        this.warnings = warnings;
    }
}
