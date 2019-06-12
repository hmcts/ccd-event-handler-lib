package uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaseDataResp {

    public final String id;

    public CaseDataResp(
        @JsonProperty("id") String id
    ) {
        this.id = id;
    }
}
