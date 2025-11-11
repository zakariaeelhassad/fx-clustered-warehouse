package com.progressoft.fxclusteredwarehouse.controller;

import com.progressoft.fxclusteredwarehouse.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String VALIDATION_MESSAGE = "validation failed";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationException(final MethodArgumentNotValidException exception , WebRequest request){
        Map<String , String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) ->{
                final String fieldName = ((FieldError) error ).getField();
                final String errorMessage = error.getDefaultMessage();
                errors.put(fieldName , errorMessage);
        });

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                VALIDATION_MESSAGE,
                request.getDescription(false),
                errors
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse runTime(final Exception exception , WebRequest request){
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                exception.getClass().getSimpleName(),
                request.getDescription(false),
                exception.getMessage()
        );
    }




}
