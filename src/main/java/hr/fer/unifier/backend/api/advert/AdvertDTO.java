package hr.fer.unifier.backend.api.advert;

import hr.fer.unifier.backend.enums.Category;
import hr.fer.unifier.backend.enums.HelpType;
import hr.fer.unifier.backend.enums.Town;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdvertDTO {

  private Long id;

  private String advertTitle;

  private Town town;

  private HelpType helpType;

  private Category category;

  private String description;

  private Long userId;

}
