package hr.fer.unifier.backend.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import hr.fer.unifier.backend.api.user.register.UserResponseDTO;
import lombok.*;

@Data
public class RequestResponseDTO {
    private Long requestId;

    private String requestTitle;

    private String location;

    private String time;

    private String helpType;

    private String category;

    private String typeOfAction;

    private String description;

    private String skillSet;

    private String volunteerCenter;

    private Integer numOfVolunteers;

    private String dealStatus;

    private boolean archived;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserResponseDTO user;

}
