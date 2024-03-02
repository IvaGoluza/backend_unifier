package hr.fer.unifier.backend.api.deal;

import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import hr.fer.unifier.backend.api.user.UserCardInfoDTO;
import lombok.Data;

@Data
public class VolunteerHelpApplicationDTO {
    private Long dealId;
    private String message;
    private AdvertResponseDTO advert;
    private UserCardInfoDTO user;
}
