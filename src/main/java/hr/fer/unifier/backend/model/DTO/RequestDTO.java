package hr.fer.unifier.backend.model.DTO;

import hr.fer.unifier.backend.model.enums.Category;
import hr.fer.unifier.backend.model.enums.HelpType;
import hr.fer.unifier.backend.model.enums.Town;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestDTO {

    private Long id;

    private boolean association;

    private String requestTitle;

    private Town town;

    private HelpType helpType;

    private Category category;

    private String description;

    private Integer volunteerNum;

    private Long userId;

}
