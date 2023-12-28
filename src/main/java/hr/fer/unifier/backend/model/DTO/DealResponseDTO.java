package hr.fer.unifier.backend.model.DTO;

import hr.fer.unifier.backend.model.enums.Sender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealResponseDTO {

    private Long id;

    private boolean accepted;

    private Sender sender;

    private RequestResponseDTO request;

    private AdvertResponseDTO advert;

}
