package com.gap.learning.gapservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GapServiceExceptionHandler {

    @ExceptionHandler(CartException.class)
    public ErrorResponse handleProjectValidationException(CartException ex, WebRequest request) {

        return new ErrorResponse(
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.toString()
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse message =
                new ErrorResponse("Method Argument Not Valid Exception",
                        errors.get(errors.keySet().iterator().next()),
                        HttpStatus.BAD_REQUEST.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
