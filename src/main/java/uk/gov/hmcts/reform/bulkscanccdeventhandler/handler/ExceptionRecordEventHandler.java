package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.CcdClient;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationResult;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.validation.CaseCreationRequestValidator;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.ExceptionRecordToCaseTransformer;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

import java.util.List;

import static java.util.Collections.emptyList;

public class ExceptionRecordEventHandler {

    private final ExceptionRecordToCaseTransformer transformer;
    private final CcdClient ccdClient;
    private final CaseCreationRequestValidator validator;

    // region constructor
    public ExceptionRecordEventHandler(
        ExceptionRecordToCaseTransformer transformer,
        CcdClient ccdClient,
        CaseCreationRequestValidator validator
    ) {
        this.transformer = transformer;
        this.ccdClient = ccdClient;
        this.validator = validator;
    }
    // endregion

    public CaseCreationResult handle(CaseCreationRequest req) {
        validator.validate(req);
        TransformationResult result = transformer.transform(req.exceptionRecord);

        boolean shouldCreateCase = result.errors.isEmpty() && (result.warnings.isEmpty() || req.ignoreWarnings);

        if (shouldCreateCase) {
            // TODO: handle exceptions
            String caseId = ccdClient.createCase(result.data, req.idamToken);
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
