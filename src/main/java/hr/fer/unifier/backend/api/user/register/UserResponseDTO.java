package hr.fer.unifier.backend.api.user.register;

import hr.fer.unifier.backend.enums.Role;
import hr.fer.unifier.backend.enums.UserType;
import lombok.Data;

@Data
public class UserResponseDTO {
  private Long id;

  private String email;

  private String mobilePhone;

  private String profileDescription;

  private Role role;

  private UserType userType;

  private boolean blocked;

  private boolean isApproved;
}
