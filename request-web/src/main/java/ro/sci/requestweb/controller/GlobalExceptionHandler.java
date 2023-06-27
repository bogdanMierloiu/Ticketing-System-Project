package ro.sci.requestweb.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.sci.requestweb.exception.NotAuthorizedForThisActionException;
import ro.sci.requestweb.exception.NotHaveAccessException;
import ro.sci.requestweb.exception.UserNotInSessionException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotInSessionException.class)
    public String handleUserNotInSessionException(UserNotInSessionException ex) {
        return "login";
    }

    @ExceptionHandler(NotHaveAccessException.class)
    public String handleUserNotHaveAccess(NotHaveAccessException ex) {
        return "access-denied";
    }
    @ExceptionHandler(NotAuthorizedForThisActionException.class)
    public String handleUserNotAuthorizedForAction(NotAuthorizedForThisActionException ex) {
        return "not-authorized";
    }


}
