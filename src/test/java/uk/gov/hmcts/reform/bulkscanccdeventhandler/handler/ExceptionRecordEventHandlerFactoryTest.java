package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.testutils.sampledata.SampleTransformationResult;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.ExceptionRecordToCaseTransformer;

import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionRecordEventHandlerFactoryTest {

    @Test
    public void should_create_an_instance_of_handler() {
        // given
        ExceptionRecordToCaseTransformer transformer = (er) -> SampleTransformationResult.okResult();
        String ccdUrl = "https://localhost:5050";
        Supplier<String> s2sTokenSupplier = () -> UUID.randomUUID().toString();

        // when
        ExceptionRecordEventHandler handler =
            ExceptionRecordEventHandlerFactory.getHandler(
                transformer,
                ccdUrl,
                s2sTokenSupplier
            );

        // then
        assertThat(handler).isNotNull();
    }
}
