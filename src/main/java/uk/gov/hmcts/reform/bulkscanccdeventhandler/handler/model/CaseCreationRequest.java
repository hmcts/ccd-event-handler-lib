package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model;

public class CaseCreationRequest {

    public final ExceptionRecord exceptionRecord;
    public final String eventId;
    public final boolean ignoreWarnings;

    // region constructor
    public CaseCreationRequest(
        ExceptionRecord exceptionRecord,
        String eventId,
        boolean ignoreWarnings
    ) {
        this.exceptionRecord = exceptionRecord;
        this.eventId = eventId;
        this.ignoreWarnings = ignoreWarnings;
    }
    // endregion
}
