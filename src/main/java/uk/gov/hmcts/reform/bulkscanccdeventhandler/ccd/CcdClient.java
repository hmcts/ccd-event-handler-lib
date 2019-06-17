package uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.CcdApi;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.CaseDataReq;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.CaseDataResp;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.Event;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.StartEventResponse;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

import java.util.function.Supplier;

public class CcdClient {

    private static final Logger log = LoggerFactory.getLogger(CcdClient.class);

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
        log.info("Starting CCD event. CaseType: {}, EventId: {}", tr.caseTypeId, tr.eventId);
        StartEventResponse startEventResponse =
            api.startEvent(
                req.idamUserId,
                req.idamToken,
                s2sTokenSupplier.get(),
                tr.jurisdiction,
                tr.caseTypeId,
                tr.eventId
            );

        log.info("Submitting CCD event. CaseType: {}, EventId: {}", tr.caseTypeId, tr.eventId);
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
