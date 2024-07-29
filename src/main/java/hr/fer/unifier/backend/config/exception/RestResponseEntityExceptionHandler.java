package hr.fer.unifier.backend.config.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class, ResponseStatusException.class, ExpiredJwtException.class})
    protected ResponseEntity<Object> handleKnownExceptions(RuntimeException ex, WebRequest request) {
        if (ex instanceof ResponseStatusException re) {
            return handleExceptionInternal(re, re.getMessage(), re.getHeaders(), re.getStatusCode(), request);
        } else if (ex instanceof EntityNotFoundException) {
            return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
        } else if (ex instanceof ExpiredJwtException) {
            return handleExceptionInternal(ex, "JWT token has expired", new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
        } else {
            return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "An unexpected error occurred", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
