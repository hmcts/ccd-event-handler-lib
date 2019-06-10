package uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer;

import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.ExceptionRecord;
import uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model.TransformationResult;

public interface ExceptionRecordToCaseTransformer {
    TransformationResult transform(ExceptionRecord er);
}
