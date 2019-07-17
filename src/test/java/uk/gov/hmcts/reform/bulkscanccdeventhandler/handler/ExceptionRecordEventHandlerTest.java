package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.CcdClient;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.exceptions.TransformationException;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationResult;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.validation.CaseCreationRequestValidator;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils.sampledata.SampleCaseCreationRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.ExceptionRecordToCaseTransformer;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.OkTransformationResult;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils.sampledata.SampleCaseCreationRequest.caseCreationRequest;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils.sampledata.SampleTransformationResult.errorResult;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils.sampledata.SampleTransformationResult.okResult;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils.sampledata.SampleTransformationResult.warningResult;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@ExtendWith(MockitoExtension.class)
public class ExceptionRecordEventHandlerTest {

    @Mock private ExceptionRecordToCaseTransformer transformer;
    @Mock private CcdClient ccdClient;
    @Mock private CaseCreationRequestValidator validator;

    private ExceptionRecordEventHandler handler;

    @BeforeEach
    private void setUp() {
        this.handler = new ExceptionRecordEventHandler(transformer, ccdClient, validator);
    }

    @Test
    public void should_handle_successful_transformation_result() {
        // given
        CaseCreationRequest req = SampleCaseCreationRequest.caseCreationRequest();
        OkTransformationResult transformationResult = okResult();

        given(ccdClient.createCase(any(), any())).willReturn("new-case-id");
        given(transformer.transform(req.exceptionRecord)).willReturn(transformationResult);

        // when
        CaseCreationResult result = handler.handle(req);

        // then
        assertThat(result.isCaseCreated()).isTrue();
        assertThat(result.caseId).isEqualTo("new-case-id");
        assertThat(result.errors).isEmpty();
        assertThat(result.warnings).isEmpty();

        verify(ccdClient).createCase(req, transformationResult);
    }

    @Test
    public void should_handle_transformation_result_with_errors() {
        // given
        CaseCreationRequest req = SampleCaseCreationRequest.caseCreationRequest();

        given(transformer.transform(req.exceptionRecord))
            .willReturn(
                errorResult(asList("warn1", "warn2"), asList("err1", "err2"))
            );

        // when
        CaseCreationResult result = handler.handle(req);

        // then
        assertThat(result.isCaseCreated()).isFalse();
        assertThat(result.caseId).isNull();
        assertThat(result.errors).containsExactly("err1", "err2");
        assertThat(result.warnings).containsExactly("warn1", "warn2");

        verify(ccdClient, never()).createCase(any(), any());
    }

    @Test
    public void should_handle_transformation_result_with_warnings_when_errors_should_not_be_ignored() {
        // given
        CaseCreationRequest req = SampleCaseCreationRequest.caseCreationRequest();

        given(transformer.transform(req.exceptionRecord))
            .willReturn(
                warningResult(asList("warn1", "warn2"))
            );

        // when
        CaseCreationResult result = handler.handle(req);

        // then
        assertThat(result.isCaseCreated()).isFalse();
        assertThat(result.caseId).isNull();
        assertThat(result.errors).isEmpty();
        assertThat(result.warnings).containsExactly("warn1", "warn2");

        verify(ccdClient, never()).createCase(any(), any());
    }

    @Test
    public void should_handle_transformation_result_with_warnings_when_errors_should_be_ignored() {
        // given
        CaseCreationRequest req = caseCreationRequest(true); // ignore warnings

        OkTransformationResult transformationResult = warningResult(asList("warn1", "warn2"));

        given(ccdClient.createCase(any(), any())).willReturn("new-case-id");
        given(transformer.transform(req.exceptionRecord)).willReturn(transformationResult);

        // when
        CaseCreationResult result = handler.handle(req);

        // then
        assertThat(result.isCaseCreated()).isTrue();
        assertThat(result.caseId).isEqualTo("new-case-id");
        assertThat(result.errors).isEmpty();
        assertThat(result.warnings).isEmpty(); // warnings removed!

        verify(ccdClient).createCase(req, transformationResult);
    }

    @Test
    public void should_validate_request() {
        // given
        CaseCreationRequest req = SampleCaseCreationRequest.caseCreationRequest();
        given(transformer.transform(req.exceptionRecord)).willReturn(okResult());

        // when
        handler.handle(req);

        // then
        verify(validator).validate(req);
    }

    @Test
    void should_throw_custom_exception_when_provided_transformer_fails() {
        // given
        CaseCreationRequest req = SampleCaseCreationRequest.caseCreationRequest();
        RuntimeException cause = new RuntimeException();
        given(transformer.transform(req.exceptionRecord)).willThrow(cause);

        // when
        Throwable exc = catchThrowable(() -> handler.handle(req));

        //then
        assertThat(exc)
            .isInstanceOf(TransformationException.class)
            .hasCause(cause);
    }
}
