package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.exceptions;

public class InvalidTransformationResultTypeException extends RuntimeException {
    public InvalidTransformationResultTypeException(String message) {
        super(message);
    }
}
