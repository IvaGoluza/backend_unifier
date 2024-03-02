package hr.fer.unifier.backend.api.deal;

import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.api.user.UserCardInfoDTO;
import lombok.Data;

@Data
public class PersonInNeedApplicationDTO {
    private Long dealId;
    private String message;
    private RequestResponseDTO request;
    private UserCardInfoDTO user;
}
