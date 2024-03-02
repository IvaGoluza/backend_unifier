package hr.fer.unifier.backend.api.request;

import hr.fer.unifier.backend.enums.Category;
import hr.fer.unifier.backend.enums.HelpType;
import hr.fer.unifier.backend.enums.UserActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestDTO {
    private String requestTitle;

    private String location;

    private String time;

    private HelpType helpType;

    private Category category;

    private String description;

    private String skillSet;

    private UserActionType typeOfAction;

    private Integer numOfVolunteers;

    private Long userId;
}
