package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.CcdClient;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecordRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecordResponse;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.ExceptionRecordToCaseTransformer;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

import java.util.List;

import static java.util.Collections.emptyList;

public class ExceptionRecordEventHandler {

    private final ExceptionRecordToCaseTransformer transformer;
    private final CcdClient ccdClient;

    // region constructor
    public ExceptionRecordEventHandler(
        ExceptionRecordToCaseTransformer transformer,
        CcdClient ccdClient
    ) {
        this.transformer = transformer;
        this.ccdClient = ccdClient;
    }
    // endregion

    public ExceptionRecordResponse handle(ExceptionRecordRequest req, String idamToken) {
        TransformationResult result = transformer.transform(req.exceptionRecord);

        if (shouldCreateCase(result, req.ignoreWarnings)) {
            String caseId = ccdClient.createCase(result.data, idamToken);
            return ok(caseId);
        } else {
            return errors(result.errors, result.warnings);
        }
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

    private ExceptionRecordResponse ok(String caseId) {
        return new ExceptionRecordResponse(caseId, emptyList(), emptyList());
    }

    private ExceptionRecordResponse errors(List<String> errors, List<String> warnings) {
        return new ExceptionRecordResponse(null, errors, warnings);
    }
}
