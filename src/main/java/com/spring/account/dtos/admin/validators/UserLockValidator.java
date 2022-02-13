package com.spring.account.dtos.admin.validators;

import com.spring.account.dtos.admin.annotations.ValidUserLockOperation;
import com.spring.account.dtos.admin.constants.UserLockOperation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Validates that the {@link java.lang.String} parameter for the user account lock operation of a {@link com.spring.account.dtos.admin.UserLockDto}
 * JSON payload is one of the values of {@link com.spring.account.dtos.admin.constants.UserLockOperation}s.
 *
 * @author Alex Giazitzis
 */
public class UserLockValidator implements ConstraintValidator<ValidUserLockOperation, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return Arrays.stream(UserLockOperation.values()).map(Enum::name).collect(Collectors.toList()).contains(value);
    }

    @Override
    public void initialize(final ValidUserLockOperation constraintAnnotation) {

    }
}
