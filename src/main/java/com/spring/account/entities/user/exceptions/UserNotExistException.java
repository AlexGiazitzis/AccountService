package com.spring.account.entities.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alex Giazitzis
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User does not exist!")
public class UserNotExistException extends RuntimeException{
}
