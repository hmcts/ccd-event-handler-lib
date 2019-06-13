package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model;

import java.util.Map;
import javax.validation.constraints.NotNull;

public class ExceptionRecord {

    @NotNull
    public final String id;

    @NotNull
    public final String jurisdiction;

    @NotNull
    public final String state;

    @NotNull
    public final String caseTypeId;

    @NotNull
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
