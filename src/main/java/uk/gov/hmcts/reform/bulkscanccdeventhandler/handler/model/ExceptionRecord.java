package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model;

import java.util.Map;

public class ExceptionRecord {

    public final String id;
    public final String jurisdiction;
    public final String state;
    public final String caseTypeId;
    public final Map<String, Object> data;

    // region constructor
    public ExceptionRecord(
        String id,
        String jurisdiction,
        String state,
        String caseTypeId,
        Map<String, Object> data
    ) {
        this.id = id;
        this.jurisdiction = jurisdiction;
        this.state = state;
        this.caseTypeId = caseTypeId;
        this.data = data;
    }
    // endregion
}
