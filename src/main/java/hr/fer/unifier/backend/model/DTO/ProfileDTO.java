package hr.fer.unifier.backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProfileDTO {

  private Long id;    //this will be userId of a user owning this profile

  private String profileDescription;

  private Long userId;

}
