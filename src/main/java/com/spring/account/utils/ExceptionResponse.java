package com.spring.account.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Custom object used for responses when runtime exceptions are triggered by user action.
 * @author Alex Giazitzis
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public class ExceptionResponse {

    LocalDateTime timestamp;
    int           status;
    String        error;
    String        message;
    String        path;

}