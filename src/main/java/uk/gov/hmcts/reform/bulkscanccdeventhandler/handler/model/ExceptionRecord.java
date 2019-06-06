package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model;

import java.util.Map;

public class ExceptionRecord {

    public final String id;
    public final String state;
    public final Map<String, Object> data;

    // region constructor
    public ExceptionRecord(String id, String state, Map<String, Object> data) {
        this.id = id;
        this.state = state;
        this.data = data;
    }
    // endregion
}

