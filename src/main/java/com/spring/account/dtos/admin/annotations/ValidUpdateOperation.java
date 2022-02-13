package com.spring.account.dtos.admin.annotations;

import com.spring.account.dtos.admin.validators.UpdateOperationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation to trigger the proper update operation validation of {@link com.spring.account.dtos.admin.UpdateUserDto}
 * by {@link com.spring.account.dtos.admin.validators.UpdateOperationValidator}.
 *
 * @author Alex Giazitzis
 */
@Documented
@Constraint(validatedBy = UpdateOperationValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUpdateOperation {

    String message() default "{Operation.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
