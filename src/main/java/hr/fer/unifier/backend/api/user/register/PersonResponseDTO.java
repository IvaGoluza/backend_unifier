package hr.fer.unifier.backend.api.user.register;

import hr.fer.unifier.backend.api.user.auth.AuthResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonResponseDTO {
    private UserResponseDTO baseInformation;

    private String firstName;

    private String lastName;

    private AuthResponseDTO auth;
}
