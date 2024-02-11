package hr.fer.unifier.backend.api.user.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String authToken;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String refreshToken;
}
