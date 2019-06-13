package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.CcdClient;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.api.CcdApi;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.ExceptionRecordToCaseTransformer;

import java.util.function.Supplier;

public final class ExceptionRecordEventHandlerFactory {

    private ExceptionRecordEventHandlerFactory() {
        // util class
    }

    public static ExceptionRecordEventHandler getHandler(
        ExceptionRecordToCaseTransformer transformer,
        String ccdUrl,
        Supplier<String> s2sTokenSupplier
    ) {
        return new ExceptionRecordEventHandler(
            transformer,
            new CcdClient(
                Feign.builder()
                    .decoder(new JacksonDecoder())
                    .encoder(new JacksonEncoder())
                    .target(CcdApi.class, ccdUrl),
                s2sTokenSupplier
            )
        );
    }
}
