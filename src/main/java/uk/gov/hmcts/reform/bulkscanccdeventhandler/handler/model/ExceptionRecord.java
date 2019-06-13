package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model;

import java.util.Map;
import javax.validation.constraints.NotEmpty;

public class ExceptionRecord {

    @NotEmpty
    public final String id;

    @NotEmpty
    public final String jurisdiction;

    @NotEmpty
    public final String state;

    @NotEmpty
    public final String caseTypeId;

    @NotEmpty
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
