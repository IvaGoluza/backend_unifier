package hr.fer.unifier.backend.validation.impl;

import hr.fer.unifier.backend.validation.FieldsMatchValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;



public class FieldsMatchValidator implements ConstraintValidator<FieldsMatchValidation, Object> {

  /**
   * String representation of field name.
   */
  private String fieldName;

  /**
   * String representation of field name which value is to be compared with value of {@link #fieldName}.
   */
  private String fieldMatchName;

  /**
   * Initializes the validator in preparation for
   * {@link #isValid(Object, ConstraintValidatorContext)} calls.
   * The constraint annotation for a given constraint declaration
   * is passed.
   * <p>
   * This method is guaranteed to be called before any use of this instance for
   * validation.
   * <p>
   * Initializes {@link #fieldName} and {@link #fieldMatchName} variables, acts like constructor.
   *
   * @param constraintAnnotation annotation instance for a given constraint declaration
   */
  @Override
  public void initialize(FieldsMatchValidation constraintAnnotation) {
    this.fieldName = constraintAnnotation.fieldName();
    this.fieldMatchName = constraintAnnotation.fieldMatchName();
  }

  /**
   * Compares values associated with field names {@link #fieldName} and {@link #fieldMatchName}.
   *
   * @param value                      object(class, interface, enum or record) containing fields that should be validated
   * @param constraintValidatorContext context in which the constraint is evaluated
   * @return true if validated fields from value object are equal or both null, false otherwise
   */
  public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

    Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(fieldName);
    Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatchName);

    if (fieldValue != null) {
      return fieldValue.equals(fieldMatchValue);
    } else {
      return fieldMatchValue == null;
    }
  }

}
