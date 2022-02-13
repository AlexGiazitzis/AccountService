package com.spring.account.entities.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alex Giazitzis
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOperationTargetException extends RuntimeException {

    public InvalidOperationTargetException(final String message) {
        super(message);
    }
}
