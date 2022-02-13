package com.spring.account.entities.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alex Giazitzis
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User exist!")
public class UserExistsException extends RuntimeException {

}
