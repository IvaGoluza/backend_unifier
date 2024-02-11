package hr.fer.unifier.backend.api.user.register;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonRegisterDTO {
    @NotNull
    private UserRegistrationDTO baseUserDetails;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
}
