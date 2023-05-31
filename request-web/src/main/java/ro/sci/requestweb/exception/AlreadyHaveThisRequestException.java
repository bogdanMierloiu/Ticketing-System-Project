package ro.sci.requestweb.exception;

public class AlreadyHaveThisRequestException extends RuntimeException {

    public AlreadyHaveThisRequestException(String message) {
        super(message);
    }
}
