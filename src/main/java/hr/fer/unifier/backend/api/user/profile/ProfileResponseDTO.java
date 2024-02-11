package hr.fer.unifier.backend.api.user.profile;

import hr.fer.unifier.backend.api.user.register.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProfileResponseDTO {

  private String profileDescription;

  private UserResponseDTO user;

}
