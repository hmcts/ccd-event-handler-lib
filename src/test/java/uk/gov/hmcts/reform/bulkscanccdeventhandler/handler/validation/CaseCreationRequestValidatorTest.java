package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.validation;


import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecord;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils.sampledata.SampleCaseCreationRequest;

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
        CaseCreationRequest req = new CaseCreationRequest(null, null, "", null, false);

        // when
        Throwable exc = catchThrowable(() -> new CaseCreationRequestValidator().validate(req));

        // then
        assertThat(exc)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("exceptionRecord must not be null")
            .hasMessageContaining("eventId must not be empty")
            .hasMessageContaining("idamToken must not be empty")
            .hasMessageContaining("idamUserId must not be empty");
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
            .hasMessageContaining("exceptionRecord.id must not be empty")
            .hasMessageContaining("exceptionRecord.data must not be empty")
            .hasMessageContaining("exceptionRecord.jurisdiction must not be empty")
            .hasMessageContaining("exceptionRecord.caseTypeId must not be empty")
            .hasMessageContaining("exceptionRecord.state must not be empty");
    }
}
