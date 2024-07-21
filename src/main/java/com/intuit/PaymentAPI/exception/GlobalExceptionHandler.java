package com.intuit.PaymentAPI.exception;

import com.intuit.PaymentAPI.dto.PaymentResponse;
import com.intuit.PaymentAPI.dto.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
ControllerAdvice
Pros:
    Centralized management to handle exceptions globally.
    Don't have to write large try catch blocks in Payment Controller.
    Ensures that all exceptions are handled consistently.
    Keeps your controllers focused on handling requests and responses, while exceptions are managed separately.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<PaymentResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new PaymentResponse(null, PaymentStatus.FAILURE, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PaymentResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new PaymentResponse(null, PaymentStatus.FAILURE, "Internal server error"));
    }
}
