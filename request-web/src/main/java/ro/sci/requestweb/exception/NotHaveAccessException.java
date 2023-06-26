package ro.sci.requestweb.exception;

public class NotHaveAccessException extends RuntimeException {
    public NotHaveAccessException(String message) {
        super(message);
    }
}
