package hr.fer.unifier.backend.api.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import hr.fer.unifier.backend.enums.Role;
import hr.fer.unifier.backend.enums.UserType;
import lombok.*;

@Data
public class UserResponseDTO {
  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  private String mobilePhone;

  private String oib;

  private String profileDescription;

  private Role role;

  private UserType userType;

  private String password;

  private boolean blocked;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String authToken;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String refreshToken;
}
