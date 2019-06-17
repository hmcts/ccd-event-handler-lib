package uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd;

import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.CcdApi;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.CaseDataReq;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.CaseDataResp;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.Event;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.StartEventResponse;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

import java.util.function.Supplier;

public class CcdClient {

    private final CcdApi api;
    private final Supplier<String> s2sTokenSupplier;

    // region constructor
    public CcdClient(CcdApi api, Supplier<String> s2sTokenSupplier) {
        this.api = api;
        this.s2sTokenSupplier = s2sTokenSupplier;
    }
    // endregion

    public String createCase(CaseCreationRequest req, TransformationResult tr) {
        // TODO: handle exceptions
        StartEventResponse startEventResponse =
            api.startEvent(
                req.idamUserId,
                req.idamToken,
                s2sTokenSupplier.get(),
                tr.jurisdiction,
                tr.caseTypeId,
                tr.eventId
            );

        CaseDataResp newCase =
            api.submitEvent(
                new CaseDataReq(
                    new Event(tr.eventId),
                    tr.data,
                    startEventResponse.token
                ),
                req.idamUserId,
                req.idamToken,
                s2sTokenSupplier.get(),
                tr.jurisdiction,
                tr.caseTypeId,
                req.ignoreWarnings
            );

        return newCase.id;
    }
}
