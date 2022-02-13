package com.spring.account.dtos.admin.annotations;

import com.spring.account.dtos.admin.validators.RoleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation to trigger proper role validation on {@link com.spring.account.dtos.admin.UpdateUserDto}
 * by {@link com.spring.account.dtos.admin.validators.RoleValidator}.
 *
 * @author Alex Giazitzis
 */
@Documented
@Constraint(validatedBy = RoleValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole {

    String message() default "{Role.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
