package com.spring.account.dtos.user.annotations;

import com.spring.account.dtos.user.validators.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation to trigger proper password validation for the user, using {@link com.spring.account.dtos.user.validators.PasswordValidator}.
 *
 * @author Alex Giazitzis
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NonBreached {

    String message() default "{Password.breached}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
