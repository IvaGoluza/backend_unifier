package hr.fer.unifier.backend.api.deal;

import lombok.Data;

@Data
public class AcceptedDealsVolunteerDTO {
    private Long dealId;
    private String personInNeedName;
    private String volunteerApplicationMessage;
    private AcceptedDealAdvertResponseDTO volunteerApplicationAdvert;
    private String personInNeedMessage;
    private AcceptedDealRequestResponseDTO personInNeedRequest;
    private boolean isReviewed;
    private boolean isContractReady;
}
