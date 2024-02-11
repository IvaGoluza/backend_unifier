package hr.fer.unifier.backend.api.user.login;

import hr.fer.unifier.backend.api.user.register.UserResponseDTO;
import hr.fer.unifier.backend.api.user.auth.AuthResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseDTO {
    private UserResponseDTO baseInformation;
    private AuthResponseDTO auth;
}
