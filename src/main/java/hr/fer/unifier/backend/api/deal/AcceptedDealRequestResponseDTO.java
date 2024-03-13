package hr.fer.unifier.backend.api.deal;

import lombok.Data;

@Data
public class AcceptedDealRequestResponseDTO {
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
}
