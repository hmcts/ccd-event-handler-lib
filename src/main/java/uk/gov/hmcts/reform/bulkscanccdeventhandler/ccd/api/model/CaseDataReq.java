package uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaseDataReq {

    @JsonProperty("event")
    public final Event event;

    @JsonProperty("data")
    public final Object data;

    @JsonProperty("event_token")
    public final String eventToken;

    public CaseDataReq(Event event, Object data, String eventToken) {
        this.event = event;
        this.data = data;
        this.eventToken = eventToken;
    }
}
