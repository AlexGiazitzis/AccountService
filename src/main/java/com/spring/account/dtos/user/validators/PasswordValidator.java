package com.spring.account.dtos.user.validators;

import com.spring.account.dtos.user.annotations.NonBreached;
import com.spring.account.services.bases.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates the {@link java.lang.String} parameter for a user's password of a JSON payload of {@link com.spring.account.dtos.user.UserDto}
 * or {@link com.spring.account.dtos.user.ChangePasswordDto} by checking if it is one of the breached passwords.
 *
 * @author Alex Giazitzis
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PasswordValidator implements ConstraintValidator<NonBreached, String> {

    UserService userService;

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return value != null && userService.isPasswordSafe(value);
    }

    @Override
    public void initialize(final NonBreached constraintAnnotation) {

    }
}
