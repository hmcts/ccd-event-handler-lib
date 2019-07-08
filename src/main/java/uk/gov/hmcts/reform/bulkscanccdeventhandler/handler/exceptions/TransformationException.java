package uk.gov.hmcts.reform.bulkscanccdeventhandler.handler.exceptions;

public class TransformationException extends RuntimeException {
    public TransformationException(String message, Throwable cause) {
        super(message, cause);
    }
}
