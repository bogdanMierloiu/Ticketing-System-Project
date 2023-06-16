package ro.sci.requestweb.exception;

public class UserNotInSessionException extends RuntimeException {
    public UserNotInSessionException(String message) {
        super(message);
    }
}
