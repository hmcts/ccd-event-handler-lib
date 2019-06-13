package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.validation;

import uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.model.CaseCreationRequest;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import static java.util.stream.Collectors.joining;

public class CaseCreationRequestValidator {

    private final Validator validator;

    public CaseCreationRequestValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public void validate(CaseCreationRequest req) {
        Set<ConstraintViolation<CaseCreationRequest>> violations = validator.validate(req);

        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(
                violations
                    .stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .collect(joining("\n"))
            );
        }
    }
}
