package uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd;

import feign.FeignException.InternalServerError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.CcdApi;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.CaseDataReq;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.CaseDataResp;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.model.StartEventResponse;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils.sampledata.SampleCaseCreationRequest.caseCreationRequest;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils.sampledata.SampleTransformationResult.okResult;

@ExtendWith(MockitoExtension.class)
public class CcdClientTest {

    @Mock private CcdApi api;
    @Mock private Supplier<String> s2sTokenSupplier;

    private CcdClient ccdClient;

    @BeforeEach
    public void setUp() {
        ccdClient = new CcdClient(api, s2sTokenSupplier);
    }

    @Test
    public void should_call_api_with_correct_params() {
        // given
        CaseCreationRequest req = caseCreationRequest();
        TransformationResult tr = okResult();
        String s2s = "s2s-token";
        given(s2sTokenSupplier.get())
            .willReturn(s2s);

        String eventToken = "event-token";
        given(api.startEvent(
            req.idamUserId,
            req.idamToken,
            s2s,
            tr.jurisdiction,
            tr.caseTypeId,
            tr.eventId
        )).willReturn(
            new StartEventResponse(eventToken)
        );

        String newCaseId = "newCaseId";
        given(api.submitEvent(
            any(),
            eq(req.idamUserId),
            eq(req.idamToken),
            eq(s2s),
            eq(tr.jurisdiction),
            eq(tr.caseTypeId),
            eq(req.ignoreWarnings)
        )).willReturn(
            new CaseDataResp(newCaseId)
        );

        // when
        String id = ccdClient.createCase(req, tr);

        // then
        assertThat(id).isEqualTo(newCaseId);

        ArgumentCaptor<CaseDataReq> argCaptor = ArgumentCaptor.forClass(CaseDataReq.class);
        verify(api).submitEvent(argCaptor.capture(), any(), any(), any(), any(), any(), anyBoolean());
        CaseDataReq createdCaseData = argCaptor.getValue();

        assertThat(createdCaseData.eventToken).isEqualTo(eventToken);
        assertThat(createdCaseData.event.id).isEqualTo(tr.eventId);
    }

    @Test
    public void should_handle_exceptions_when_starting_a_ccd_event() {
        // given
        InternalServerError error = new InternalServerError("error", null);
        given(api.startEvent(any(), any(), any(), any(), any(), any())).willThrow(error);

        // when
        Throwable exc = catchThrowable(() -> ccdClient.createCase(caseCreationRequest(), okResult()));

        // then
        assertThat(exc)
            .isInstanceOf(CcdException.class)
            .hasMessage("Error starting CCD event")
            .hasCause(error);
    }

    @Test
    public void should_handle_exceptions_when_submitting_a_ccd_event() {
        // given
        InternalServerError error = new InternalServerError("error", null);
        given(api.startEvent(any(), any(), any(), any(), any(), any()))
            .willReturn(new StartEventResponse("token"));
        given(api.submitEvent(any(), any(), any(), any(), any(), any(), anyBoolean()))
            .willThrow(error);

        // when
        Throwable exc = catchThrowable(() -> ccdClient.createCase(caseCreationRequest(), okResult()));

        // then
        assertThat(exc)
            .isInstanceOf(CcdException.class)
            .hasMessage("Error submitting CCD event")
            .hasCause(error);
    }
}
