package hr.fer.unifier.backend.api.user;

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

  private String authToken;

  private String refreshToken;
}
