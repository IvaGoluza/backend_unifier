package hr.fer.unifier.backend.api.user;

import lombok.Data;

@Data
public class PasswordResetTokenRequestDTO {
    private String email;
}
