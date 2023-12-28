package hr.fer.unifier.backend.validation;


import hr.fer.unifier.backend.validation.impl.FieldsMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Values targeted by this annotation must be equal.
 */
@SuppressWarnings("unused")
@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = FieldsMatchValidator.class)
public @interface FieldsMatchValidation {

  String message() default "Fields match validation.";

  String fieldName();

  String fieldMatchName();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
