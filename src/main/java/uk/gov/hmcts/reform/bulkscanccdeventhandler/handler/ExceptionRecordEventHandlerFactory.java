package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import com.google.common.base.Strings;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.CcdClient;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.CcdApi;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.validation.CaseCreationRequestValidator;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.ExceptionRecordToCaseTransformer;

import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class ExceptionRecordEventHandlerFactory {

    private ExceptionRecordEventHandlerFactory() {
        // util class
    }

    /**
     * Creates and instance of ExceptionRecordEventHandler based on parameters provided.
     *
     * @param transformer      Transformer that should be used when converting CCD Exception Record to a case.
     * @param ccdUrl           Base URL to CCD.
     * @param s2sTokenSupplier function returning your service's s2s token. It will be used when creating a case in CCD.
     * @return an instance of ExceptionRecordEventHandler
     */
    public static ExceptionRecordEventHandler getHandler(
        ExceptionRecordToCaseTransformer transformer,
        String ccdUrl,
        Supplier<String> s2sTokenSupplier
    ) {
        checkNotNull(transformer);
        checkArgument(!Strings.isNullOrEmpty(ccdUrl));
        checkNotNull(s2sTokenSupplier);

        return new ExceptionRecordEventHandler(
            transformer,
            new CcdClient(
                Feign.builder()
                    .decoder(new JacksonDecoder())
                    .encoder(new JacksonEncoder())
                    .target(CcdApi.class, ccdUrl),
                s2sTokenSupplier
            ),
            new CaseCreationRequestValidator()
        );
    }
}
