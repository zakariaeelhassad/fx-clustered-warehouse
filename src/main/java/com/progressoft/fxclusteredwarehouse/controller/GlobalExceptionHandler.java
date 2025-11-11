package com.progressoft.fxclusteredwarehouse.controller;

import com.progressoft.fxclusteredwarehouse.exception.DealIdException;
import com.progressoft.fxclusteredwarehouse.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(DealIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDealIdException(final DealIdException exception, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                exception.getClass().getSimpleName(),
                request.getDescription(false),
                exception.getMessage()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception, WebRequest request) {
        String customMessage = "Invalid request format or data type. Check Currency or Date fields.";

        String detail = exception.getMessage();
        if (detail != null && detail.contains("Unrecognized currency")) {
            customMessage = "Unrecognized currency code. Please enter a valid ISO 4217 currency code (e.g., 'USD', 'EUR').";
        }

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                exception.getClass().getSimpleName(),
                request.getDescription(false),
                customMessage
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
