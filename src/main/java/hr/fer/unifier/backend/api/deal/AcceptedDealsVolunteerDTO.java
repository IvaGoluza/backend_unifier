package hr.fer.unifier.backend.api.deal;

import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import lombok.Data;

@Data
public class AcceptedDealsVolunteerDTO {
    private String personInNeedName;
    private String volunteerApplicationMessage;
    private AdvertResponseDTO volunteerApplicationAdvert;
    private String personInNeedMessage;
    private RequestResponseDTO personInNeedRequest;
    private boolean isReviewed;
}
