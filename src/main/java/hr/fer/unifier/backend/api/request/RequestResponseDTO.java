package hr.fer.unifier.backend.api.request;

import hr.fer.unifier.backend.api.user.register.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestResponseDTO {

    private String requestTitle;

    private String location;

    private String time;

    private String helpType;

    private String category;

    private String description;

    private String skillSet;

    private String volunteerCenter;

    private Integer numOfVolunteers;

    private UserResponseDTO user;

}
