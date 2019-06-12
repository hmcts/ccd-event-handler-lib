package uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StartEventResponse {

    public final String token;

    public StartEventResponse(
        @JsonProperty("token") String token
    ) {
        this.token = token;
    }
}
