package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.validation;


import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecord;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.testutils.sampledata.SampleCaseCreationRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CaseCreationRequestValidatorTest {

    @Test
    void should_not_throw_an_exception_if_object_is_valid() {
        // given
        CaseCreationRequest req = SampleCaseCreationRequest.caseCreationRequest();

        // when
        Throwable exc = catchThrowable(() -> new CaseCreationRequestValidator().validate(req));

        // then
        assertThat(exc).isNull();
    }

    @Test
    void should_throw_an_exception_if_there_are_missing_fields() {
        // given
        CaseCreationRequest req = new CaseCreationRequest(null, null, null, null, false);

        // when
        Throwable exc = catchThrowable(() -> new CaseCreationRequestValidator().validate(req));

        // then
        assertThat(exc)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("exceptionRecord must not be null")
            .hasMessageContaining("eventId must not be null")
            .hasMessageContaining("idamToken must not be null")
            .hasMessageContaining("idamUserId must not be null");
    }

    @Test
    void should_throw_an_exception_if_nested_exception_record_is_invalid() {
        // given
        CaseCreationRequest req = new CaseCreationRequest(
            new ExceptionRecord(null, null, null, null, null),
            "evnt",
            "token",
            "id",
            false
        );

        // when
        Throwable exc = catchThrowable(() -> new CaseCreationRequestValidator().validate(req));

        // then
        assertThat(exc)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("exceptionRecord.id must not be null")
            .hasMessageContaining("exceptionRecord.data must not be null")
            .hasMessageContaining("exceptionRecord.jurisdiction must not be null")
            .hasMessageContaining("exceptionRecord.caseTypeId must not be null")
            .hasMessageContaining("exceptionRecord.state must not be null");
    }
}