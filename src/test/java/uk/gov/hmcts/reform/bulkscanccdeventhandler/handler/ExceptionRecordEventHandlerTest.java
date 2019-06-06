package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.CcdClient;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecordRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecordResponse;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.ExceptionRecordToCaseTransformer;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.testutils.sampledata.SampleExceptionRecordRequest.exceptionRecordRequest;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.testutils.sampledata.SampleTransformationResult.errorResult;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.testutils.sampledata.SampleTransformationResult.okResult;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.testutils.sampledata.SampleTransformationResult.warningResult;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@ExtendWith(MockitoExtension.class)
public class ExceptionRecordEventHandlerTest {

    @Mock private Supplier<String> s2sTokenSupplier;
    @Mock private ExceptionRecordToCaseTransformer transformer;
    @Mock private CcdClient ccdClient;

    private ExceptionRecordEventHandler handler;

    @BeforeEach
    private void setUp() {
        this.handler = new ExceptionRecordEventHandler(s2sTokenSupplier, transformer, ccdClient);
    }

    @Test
    public void should_handle_successful_transformation_result() {
        // given
        ExceptionRecordRequest req = exceptionRecordRequest();
        String idamToken = "some-idam-token";
        String s2sToken = "some-s2s-token";

        TransformationResult transformationResult = okResult();

        given(s2sTokenSupplier.get()).willReturn(s2sToken);
        given(transformer.transform(req.exceptionRecord)).willReturn(transformationResult);

        // when
        ExceptionRecordResponse resp = handler.handle(req, idamToken);

        // then
        assertThat(resp.errors).isEmpty();
        assertThat(resp.warnings).isEmpty();

        verify(ccdClient).createCase(transformationResult.data, idamToken, s2sToken);
    }

    @Test
    public void should_handle_transformation_result_with_errors() {
        // given
        ExceptionRecordRequest req = exceptionRecordRequest();

        given(transformer.transform(req.exceptionRecord))
            .willReturn(
                errorResult(asList("warn1", "warn2"), asList("err1", "err2"))
            );

        // when
        ExceptionRecordResponse resp = handler.handle(req, "idam-token");

        // then
        assertThat(resp.errors).containsExactly("err1", "err2");
        assertThat(resp.warnings).containsExactly("warn1", "warn2");

        verify(ccdClient, never()).createCase(any(), any(), any());
    }

    @Test
    public void should_transformation_result_with_warnings_when_errors_should_not_be_ignored() {
        // given
        ExceptionRecordRequest req = exceptionRecordRequest();

        given(transformer.transform(req.exceptionRecord))
            .willReturn(
                warningResult(asList("warn1", "warn2"))
            );

        // when
        ExceptionRecordResponse resp = handler.handle(req, "idam-token");

        // then
        assertThat(resp.errors).isEmpty();
        assertThat(resp.warnings).containsExactly("warn1", "warn2");

        verify(ccdClient, never()).createCase(any(), any(), any());
    }

    @Test
    public void should_transformation_result_with_warnings_when_errors_should_be_ignored() {
        // given
        ExceptionRecordRequest req = exceptionRecordRequest(true); // ignore warnings
        String idamToken = "some-idam-token";
        String s2sToken = "some-s2s-token";

        TransformationResult transformationResult = warningResult(asList("warn1", "warn2"));

        given(s2sTokenSupplier.get()).willReturn(s2sToken);
        given(transformer.transform(req.exceptionRecord)).willReturn(transformationResult);

        // when
        ExceptionRecordResponse resp = handler.handle(req, idamToken);

        // then
        assertThat(resp.errors).isEmpty();
        assertThat(resp.warnings).isEmpty(); // warnings removed!

        verify(ccdClient).createCase(transformationResult.data, idamToken, s2sToken);
    }
}