package hr.fer.unifier.backend.api.deal;

import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.enums.Sender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealResponseDTO {

    private Long dealId;

    private boolean accepted;

    private Sender sender;

    private RequestResponseDTO request;

    private AdvertResponseDTO advert;

}
