package hr.fer.unifier.backend.api.advert;

import hr.fer.unifier.backend.enums.Category;
import hr.fer.unifier.backend.enums.HelpType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdvertDTO {
  private String advertTitle;

  private String location;

  private HelpType helpType;

  private Category category;

  private String description;

  private String time;

  private Long userId;
}
