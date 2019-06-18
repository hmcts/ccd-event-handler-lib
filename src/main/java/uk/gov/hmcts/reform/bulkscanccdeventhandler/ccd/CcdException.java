package uk.gov.hmcts.reform.bulkscanccdeventhandler.ccd;

public class CcdException extends RuntimeException {
    public CcdException(String message, Throwable cause) {
        super(message, cause);
    }
}
