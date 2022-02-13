package com.spring.account.entities.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alex Giazitzis
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found!")
public class UserNotFoundException extends RuntimeException{
}
