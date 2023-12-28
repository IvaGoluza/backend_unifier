package hr.fer.unifier.backend.validation;


import hr.fer.unifier.backend.validation.impl.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotated element must be a valid email.
 */
@SuppressWarnings("unused")
@Target({METHOD, TYPE, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailValidation {

  String message() default "Email validation.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
