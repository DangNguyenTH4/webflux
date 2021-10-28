package com.example.webflux.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerCustomer {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlxception(Exception e){
        log.info("Catched ExceptionHandler when validate request!");
        Map<String, String> errors = new HashMap();

        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * With spring web flux throw by WebExchangeBindException
     * @param e
     * @return
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<?> handlWebExchangeBindException(WebExchangeBindException e){
        log.info("Catched WebExchangeBindException when validate request!");
        Map<String, String> errors = new HashMap();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }


    /**
     * Normal spring api
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.info("Catched MethodArgumentNotValidException.class");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
