package hr.fer.unifier.backend.api.advert;

import com.fasterxml.jackson.annotation.JsonInclude;
import hr.fer.unifier.backend.api.user.register.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdvertResponseDTO {

  private Long advertId;

  private String advertTitle;

  private String location;

  private String helpType;

  private String category;

  private String description;

  private String advertImage;

  private String volunteerCenter;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private UserResponseDTO user;
}
