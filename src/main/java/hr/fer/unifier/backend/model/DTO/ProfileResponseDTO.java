package hr.fer.unifier.backend.model.DTO;

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
