package hr.fer.unifier.backend.api.user;

import lombok.Data;

@Data
public class ResetPasswordRequestDTO {
    private String password;
    private String token;
}
