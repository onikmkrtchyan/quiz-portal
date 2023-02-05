package quizportal.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleResourceNotFoundException(HttpServletRequest req, ResourceNotFoundException e) {
        logError(req, e);
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity<?> handlePasswordMismatchException(HttpServletRequest req, PasswordMismatchException e) {
        logError(req, e);
        return buildResponse(HttpStatus.CONFLICT, e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity<?> handleUsernameAlreadyTakenException(HttpServletRequest req, UsernameAlreadyTakenException e) {
        logError(req, e);
        return buildResponse(HttpStatus.CONFLICT, e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity<?> handleEmailAlreadyTakenException(HttpServletRequest req, EmailAlreadyTakenException e) {
        logError(req, e);
        return buildResponse(HttpStatus.CONFLICT, e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleRefreshTokenNotFoundException(HttpServletRequest req, RefreshTokenNotFoundException e) {
        logError(req, e);
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage(), req.getRequestURI());
    }

    private ResponseEntity<?> buildResponse(HttpStatus httpCode, String message, String requestURI) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", message);
        errors.put("URL", requestURI);
        return ResponseEntity.status(httpCode).body(errors);
    }

    private void logError(HttpServletRequest req, Exception e) {
        LOGGER.warn(e.getMessage());
        LOGGER.warn("RequestURI {}", req.getRequestURI());
        LOGGER.error(ExceptionUtils.getStackTrace(e));
    }
}
