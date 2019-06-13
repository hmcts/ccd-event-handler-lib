package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class CaseCreationRequest {

    @Valid
    @NotNull
    public final ExceptionRecord exceptionRecord;

    @NotNull
    public final String eventId;

    @NotNull
    public final String idamToken;

    @NotNull
    public final String idamUserId;

    public final boolean ignoreWarnings;

    // region constructor
    public CaseCreationRequest(
        ExceptionRecord exceptionRecord,
        String eventId,
        String idamToken,
        String idamUserId,
        boolean ignoreWarnings
    ) {
        this.exceptionRecord = exceptionRecord;
        this.eventId = eventId;
        this.idamToken = idamToken;
        this.idamUserId = idamUserId;
        this.ignoreWarnings = ignoreWarnings;
    }
    // endregion
}
