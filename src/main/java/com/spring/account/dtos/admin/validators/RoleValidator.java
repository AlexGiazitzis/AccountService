package com.spring.account.dtos.admin.validators;

import com.spring.account.dtos.admin.annotations.ValidRole;
import com.spring.account.entities.user.constants.UserRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Validates the {@link java.lang.String} parameter for the role of the JSON payload {@link com.spring.account.dtos.admin.UpdateUserDto}
 * in order to make sure it's one of the {@link com.spring.account.entities.user.constants.UserRole}s.
 *
 * @author Alex Giazitzis
 */
public class RoleValidator implements ConstraintValidator<ValidRole, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return Arrays.stream(UserRole.values()).map(Enum::name).collect(Collectors.toList()).contains(value);
    }

    @Override
    public void initialize(final ValidRole constraintAnnotation) {

    }

}
