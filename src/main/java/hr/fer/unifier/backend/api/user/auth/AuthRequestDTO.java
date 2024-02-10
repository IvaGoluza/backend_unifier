package hr.fer.unifier.backend.api.user.auth;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String refreshToken;
}
