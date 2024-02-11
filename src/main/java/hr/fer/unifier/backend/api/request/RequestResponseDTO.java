package hr.fer.unifier.backend.api.request;

import hr.fer.unifier.backend.api.user.register.UserResponseDTO;
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
public class RequestResponseDTO {

    private Long id;

    private boolean association;

    private String requestTitle;

    private Town town;

    private HelpType helpType;

    private Category category;

    private String description;

    private Integer volunteerNum;

    private boolean active;

    private UserResponseDTO user;

}
