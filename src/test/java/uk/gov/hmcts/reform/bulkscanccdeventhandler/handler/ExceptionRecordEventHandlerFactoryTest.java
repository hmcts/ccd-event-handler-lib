package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.testutils.sampledata.SampleTransformationResult;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.ExceptionRecordToCaseTransformer;

import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.ExceptionRecordEventHandlerFactory.getHandler;

@SuppressWarnings("checkstyle:LineLength")
public class ExceptionRecordEventHandlerFactoryTest {

    private final ExceptionRecordToCaseTransformer transformer = (er) -> SampleTransformationResult.okResult();
    private final String ccdUrl = "https://localhost:5050";
    private final Supplier<String> s2sTokenSupplier = () -> UUID.randomUUID().toString();

    @Test
    public void should_create_an_instance_of_handler() {
        // when
        ExceptionRecordEventHandler handler = getHandler(transformer, ccdUrl, s2sTokenSupplier);

        // then
        assertThat(handler).isNotNull();
    }

    @Test
    void should_require_all_arguments_to_be_present() {
        assertThatThrownBy(() -> getHandler(null, ccdUrl, s2sTokenSupplier)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> getHandler(transformer, null, s2sTokenSupplier)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> getHandler(transformer, "", s2sTokenSupplier)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> getHandler(transformer, ccdUrl, null)).isInstanceOf(NullPointerException.class);
    }
}
