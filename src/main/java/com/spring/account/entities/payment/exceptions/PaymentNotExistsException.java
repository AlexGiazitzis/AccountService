package com.spring.account.entities.payment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alex Giazitzis
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Payment for specified period doesn't exist.")
public class PaymentNotExistsException extends RuntimeException {
}
