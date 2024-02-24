package hr.fer.unifier.backend.api.user.register;

import hr.fer.unifier.backend.enums.VolunteerCenter;
import hr.fer.unifier.backend.validation.EmailValidation;
import hr.fer.unifier.backend.validation.FieldsMatchValidation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@FieldsMatchValidation(
        message = "Passwords do not match.",
        fieldName = "password",
        fieldMatchName = "controlPassword"
)
public class PersonRegisterDTO {

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
    private String password;

    @NotNull
    private String controlPassword;

    @NotNull
    private VolunteerCenter volunteerCenter;

    @NotNull
    private UserTypeRequestDTO userType;
}
