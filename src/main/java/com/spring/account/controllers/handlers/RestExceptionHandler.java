package com.spring.account.controllers.handlers;

import com.spring.account.utils.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

/**
 * Controller-wide {@link org.springframework.web.bind.annotation.ExceptionHandler} provider.
 * Addresses two validation exceptions, {@link org.springframework.web.bind.MethodArgumentNotValidException} and
 * {@link javax.validation.ConstraintViolationException}, for which it returns a custom JSON response to the user, notifying him what went wrong.
 *
 * @author Alex Giazitzis
 */
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                   ServletWebRequest request) {

        return new ExceptionResponse(LocalDateTime.now(),
                                     400,
                                     "Bad Request",
                                     ex.getFieldError() != null
                                     ? ex.getFieldError().getDefaultMessage()
                                     : "Default message. Please open a bug report with details of what you did and this came up.",
                                     request.getRequest().getServletPath());

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException ex,
                                                                ServletWebRequest request) {

        String message = ex.getMessage();
        return new ExceptionResponse(LocalDateTime.now(),
                                     400,
                                     "Bad Request",
                                     message != null
                                     ? message
                                     : "Default message. Please open a bug report with details of what you did and this came up.",
                                     request.getRequest().getServletPath());

    }
}
