package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler;

import uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd.CcdClient;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.exceptions.InvalidTransformationResultTypeException;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.exceptions.TransformationException;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationRequest;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationResult;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.validation.CaseCreationRequestValidator;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.ExceptionRecordToCaseTransformer;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.ErrorTransformationResult;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.OkTransformationResult;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

import java.util.List;
import java.util.function.Function;

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

        final TransformationResult result;
        try {
            result = transformer.transform(req.exceptionRecord);
        } catch (Exception exc) {
            throw new TransformationException("Error while calling provided transformer", exc);
        }

        return handleTransformationResult(
            result,
            okRes -> {
                if (okRes.warnings.isEmpty() || req.ignoreWarnings) {
                    // TODO: handle exceptions
                    String caseId = ccdClient.createCase(req, okRes);
                    return ok(caseId);
                } else {
                    return errors(emptyList(), okRes.warnings);
                }
            },
            errRes -> errors(errRes.errors, errRes.warnings)
        );
    }

    private <T> T handleTransformationResult(
        TransformationResult result,
        Function<OkTransformationResult, T> okAction,
        Function<ErrorTransformationResult, T> errorAction
    ) {
        if (result instanceof OkTransformationResult) {
            return okAction.apply((OkTransformationResult) result);
        } else if (result instanceof ErrorTransformationResult) {
            return errorAction.apply((ErrorTransformationResult) result);
        } else {
            throw new InvalidTransformationResultTypeException("Invalid type: " + result.getClass().getName());
        }
    }

    private CaseCreationResult ok(String caseId) {
        return new CaseCreationResult(caseId, emptyList(), emptyList());
    }

    private CaseCreationResult errors(List<String> errors, List<String> warnings) {
        return new CaseCreationResult(null, errors, warnings);
    }
}
