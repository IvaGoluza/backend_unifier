package hr.fer.unifier.backend.api.deal;

import lombok.Data;

@Data
public class AcceptedPersonInNeedDealsDTO {
    private Long dealId;
    private Long volunteerId;
    private String volunteerName;
    private String volunteerApplicationMessage;
    private AcceptedDealAdvertResponseDTO volunteerApplicationAdvert;
    private String personInNeedMessage;
    private AcceptedDealRequestResponseDTO personInNeedRequest;
    private boolean isRecensionFulfilled;
    private boolean isContractDetailsFulfilled;
}
