package uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model;

import java.util.List;

import static java.util.Collections.emptyList;

public class OkTransformationResult extends TransformationResult {

    public OkTransformationResult(
        List<String> warnings,
        Object data,
        String jurisdiction,
        String caseTypeId,
        String eventId
    ) {
        super(warnings, emptyList(), data, jurisdiction, caseTypeId, eventId);
    }
}
