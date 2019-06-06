package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.CcdClient;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecordRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecordResponse;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.ExceptionRecordToCaseTransformer;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

import java.util.function.Supplier;

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

        if (shouldCreateCase(result, req.ignoreWarnings)) {
            ccdClient.createCase(result.data, idamToken, s2sTokenSupplier.get());
        }
        return new ExceptionRecordResponse(result.errors, result.warnings);
    }

    private boolean shouldCreateCase(TransformationResult result, boolean ignoreWarnings) {
        if (!result.errors.isEmpty()) {
            return false;
        } else if (!result.warnings.isEmpty()) {
            return ignoreWarnings ? true : false;
        } else {
            return true;
        }
    }

}
