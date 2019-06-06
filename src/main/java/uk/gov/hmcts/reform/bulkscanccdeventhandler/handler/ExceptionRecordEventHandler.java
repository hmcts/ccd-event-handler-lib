package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.CcdClient;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecordRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecordResponse;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.ExceptionRecordToCaseTransformer;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

import java.util.function.Supplier;

import static java.util.Collections.emptyList;

public class ExceptionRecordEventHandler {

    private final Supplier<String> s2sTokenSupplier;
    private final ExceptionRecordToCaseTransformer transformer;
    private final CcdClient ccdClient;

    // region constructor
    public ExceptionRecordEventHandler(
        Supplier<String> s2sTokenSupplier,
        ExceptionRecordToCaseTransformer transformer,
        CcdClient ccdClient
    ) {
        this.s2sTokenSupplier = s2sTokenSupplier;
        this.transformer = transformer;
        this.ccdClient = ccdClient;
    }
    // endregion

    public ExceptionRecordResponse handle(ExceptionRecordRequest req, String idamToken) {
        TransformationResult result = transformer.transform(req.exceptionRecord);
        switch (result.status) {
            case OK:
                ccdClient.createCase(result.data, idamToken, s2sTokenSupplier.get());
                return new ExceptionRecordResponse(emptyList(), emptyList());
            case WARNINGS:
                if (req.ignoreWarnings) {
                    ccdClient.createCase(result.data, idamToken, s2sTokenSupplier.get());
                    return new ExceptionRecordResponse(emptyList(), emptyList());
                } else {
                    return new ExceptionRecordResponse(emptyList(), result.warnings);
                }
            case ERRORS:
                return new ExceptionRecordResponse(result.errors, result.warnings);
            default:
                throw new IllegalArgumentException("Unhandled status " + result.status);
        }
    }
}
