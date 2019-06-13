package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model;

public class CaseCreationRequest {

    public final ExceptionRecord exceptionRecord;
    public final String eventId;
    public final String idamToken;
    public final boolean ignoreWarnings;

    // region constructor

    public CaseCreationRequest(
        ExceptionRecord exceptionRecord,
        String eventId,
        String idamToken,
        boolean ignoreWarnings
    ) {
        this.exceptionRecord = exceptionRecord;
        this.eventId = eventId;
        this.idamToken = idamToken;
        this.ignoreWarnings = ignoreWarnings;
    }

    // endregion
}
