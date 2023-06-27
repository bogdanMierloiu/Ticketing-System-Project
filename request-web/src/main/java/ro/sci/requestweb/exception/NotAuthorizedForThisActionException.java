package ro.sci.requestweb.exception;

public class NotAuthorizedForThisActionException extends RuntimeException {

    public NotAuthorizedForThisActionException(String message) {
        super(message);
    }
}
