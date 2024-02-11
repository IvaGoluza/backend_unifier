package hr.fer.unifier.backend.api.user.auth;

import hr.fer.unifier.backend.enums.Role;
import hr.fer.unifier.backend.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponseDTO {
    private Long id;

    private String email;

    private String mobilePhone;

    private Role role;

    private UserType userType;

    private boolean blocked;

    private AuthTokenDTO auth;
}
