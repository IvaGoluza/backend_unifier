package hr.fer.unifier.backend.api.user;

import hr.fer.unifier.backend.enums.UserType;
import hr.fer.unifier.backend.validation.EmailValidation;
import hr.fer.unifier.backend.validation.FieldsMatchValidation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldsMatchValidation(
    message = "Passwords do not match.",
    fieldName = "password",
    fieldMatchName = "controlPassword"
)
public class UserRegistrationDTO {

  private Long id;

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

  @NotNull
  @EmailValidation(message = "Email is invalid.")
  private String email;

  @NotNull
  private String mobilePhone;

  @NotNull
  private String profileDescription;

  @NotNull
  private String oib;

  @NotNull
  private String password;

  @NotNull
  private String controlPassword;

  @NotNull
  private UserType userType;
}
