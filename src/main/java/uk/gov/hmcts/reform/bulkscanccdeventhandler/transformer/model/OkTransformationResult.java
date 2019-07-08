package uk.gov.hmcts.reform.bulkscanccdeventhandler.transformer.model;

import java.util.List;

public class OkTransformationResult implements TransformationResult {

    /**
     * Warnings that occurred during case mapping or validation.
     */
    public final List<String> warnings;

    /**
     * Object representing the case that should be created in CCD.
     * It will be serialised to json. It can be a map or a domain object, as long as it serialises to expected json.
     */
    public final Object data;

    /**
     * CCD jurisdiction in which case should be created.
     */
    public final String jurisdiction;

    /**
     * CCD CaseTypeId that should be used when creating a case.
     */
    public final String caseTypeId;

    /**
     * CCD Event Id that should be used when creating a case.
     */
    public final String eventId;

    public OkTransformationResult(
        List<String> warnings,
        Object data,
        String jurisdiction,
        String caseTypeId,
        String eventId
    ) {
        this.warnings = warnings;
        this.data = data;
        this.jurisdiction = jurisdiction;
        this.caseTypeId = caseTypeId;
        this.eventId = eventId;
    }
}
