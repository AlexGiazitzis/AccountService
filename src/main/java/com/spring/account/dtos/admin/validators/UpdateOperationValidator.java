package com.spring.account.dtos.admin.validators;

import com.spring.account.dtos.admin.annotations.ValidUpdateOperation;
import com.spring.account.dtos.admin.constants.UpdateOperation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Validates the {@link java.lang.String} parameter for the update to be performed on a user's account from the JSON
 * payload of {@link com.spring.account.dtos.admin.UpdateUserDto} so that it is one of the {@link com.spring.account.dtos.admin.constants.UpdateOperation}s.
 *
 * @author Alex Giazitzis
 */
public class UpdateOperationValidator implements ConstraintValidator<ValidUpdateOperation, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return Arrays.stream(UpdateOperation.values()).map(Enum::name).collect(Collectors.toList()).contains(value);
    }

    @Override
    public void initialize(final ValidUpdateOperation constraintAnnotation) {

    }
}
