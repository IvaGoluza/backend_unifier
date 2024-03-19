package hr.fer.unifier.backend.api.deal;

import lombok.Data;

@Data
public class AcceptedDealAdvertResponseDTO {
    private Long advertId;

    private String advertTitle;

    private String location;

    private String helpType;

    private String category;

    private String description;

    private String volunteerCenter;

    private String time;

    private boolean hasImage;
}
