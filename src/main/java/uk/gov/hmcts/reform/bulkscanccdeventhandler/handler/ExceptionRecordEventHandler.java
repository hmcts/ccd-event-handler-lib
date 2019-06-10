package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.CcdClient;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationResult;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecordRequest;
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

    public CaseCreationResult handle(ExceptionRecordRequest req, String idamToken) {
        TransformationResult result = transformer.transform(req.exceptionRecord);

        boolean shouldCreateCase = result.errors.isEmpty() && (result.warnings.isEmpty() || req.ignoreWarnings);

        if (shouldCreateCase) {
            // TODO: handle exceptions
            String caseId = ccdClient.createCase(result.data, idamToken);
            return ok(caseId);
        } else {
            return errors(result.errors, result.warnings);
        }
    }

    private CaseCreationResult ok(String caseId) {
        return new CaseCreationResult(caseId, emptyList(), emptyList());
    }

    private CaseCreationResult errors(List<String> errors, List<String> warnings) {
        return new CaseCreationResult(null, errors, warnings);
    }
}
