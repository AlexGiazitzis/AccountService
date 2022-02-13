package com.spring.account.dtos.admin.annotations;

import com.spring.account.dtos.admin.validators.UserLockValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation to trigger the proper user account lock operation validation of {@link com.spring.account.dtos.admin.UserLockDto}
 * by {@link com.spring.account.dtos.admin.validators.UserLockValidator}.
 *
 * @author Alex Giazitzis
 */
@Documented
@Constraint(validatedBy = UserLockValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserLockOperation {

    String message() default "{Operation.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
