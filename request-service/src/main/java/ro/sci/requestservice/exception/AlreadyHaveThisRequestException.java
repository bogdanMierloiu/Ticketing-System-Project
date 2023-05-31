package ro.sci.requestservice.exception;

public class AlreadyHaveThisRequestException extends RuntimeException {

    public AlreadyHaveThisRequestException(String message) {
        super(message);
    }
}
