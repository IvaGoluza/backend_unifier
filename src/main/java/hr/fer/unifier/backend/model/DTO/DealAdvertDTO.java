package hr.fer.unifier.backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealAdvertDTO {

    private Long id;

    private boolean accepted;

    private AdvertResponseDTO advert;

}
