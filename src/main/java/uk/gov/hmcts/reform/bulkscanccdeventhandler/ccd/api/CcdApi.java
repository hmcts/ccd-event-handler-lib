package uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.CaseDataReq;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.CaseDataResp;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.StartEventResponse;

@SuppressWarnings("checkstyle:LineLength")
public interface CcdApi {

    @Headers({
        "Content-Type: application/json",
        "Authorization: {idamToken}",
        "ServiceAuthorization: {s2sToken}"
    })
    @RequestLine(
        "GET /caseworkers/{userId}/jurisdictions/{jurisdiction}/case-types/{caseType}/event-triggers/{eventId}/token"
    )
    StartEventResponse startEvent(
        @Param("userId") String userId,
        @Param("idamToken") String idamToken,
        @Param("s2sToken") String s2sToken,
        @Param("jurisdiction") String jurisdiction,
        @Param("caseType") String caseType,
        @Param("eventId") String eventId
    );

    @Headers({
        "Content-Type: application/json",
        "Authorization: {idamToken}",
        "ServiceAuthorization: {s2sToken}"
    })
    @RequestLine(
        "POST /caseworkers/{userId}/jurisdictions/{jurisdiction}/case-types/{caseType}/cases?ignore-warning={ignoreWarning}"
    )
    CaseDataResp submitEvent(
        CaseDataReq caseDataReq,
        @Param("userId") String userId,
        @Param("idamToken") String idamToken,
        @Param("s2sToken") String s2sToken,
        @Param("jurisdiction") String jurisdiction,
        @Param("caseType") String caseType,
        @Param("ignoreWarning") boolean ignoreWarning
    );
}
