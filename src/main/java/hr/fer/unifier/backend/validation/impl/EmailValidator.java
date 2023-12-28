package hr.fer.unifier.backend.validation.impl;



import hr.fer.unifier.backend.validation.EmailValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile(
          "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
              + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");


  /**
   * Initializes the validator in preparation for
   * {@link #isValid(String, ConstraintValidatorContext)} calls.
   * The constraint annotation for a given constraint declaration
   * is passed.
   * <p>
   * This method is guaranteed to be called before any use of this instance for
   * validation.
   *
   * @param constraintAnnotation annotation instance for a given constraint declaration
   */
  @Override
  public void initialize(EmailValidation constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  /**
   * Email validation method.
   *
   * @param value                      string representation of an email
   * @param constraintValidatorContext context in which the constraint is evaluated
   * @return false if entered email is in the wrong format, true otherwise
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    if (value == null) {
      return true;
    }

    return (value.equals("") || EMAIL_PATTERN.matcher(value).matches());
  }
}
