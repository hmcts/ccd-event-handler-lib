package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model;

public class CaseCreationRequest {

    public final ExceptionRecord exceptionRecord;
    public final String eventId;
    public final String idamToken;
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
