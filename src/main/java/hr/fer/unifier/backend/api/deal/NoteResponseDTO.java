package hr.fer.unifier.backend.api.deal;

import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NoteResponseDTO {

    private Long id;

    private String note;

    private AdvertResponseDTO advert;

}
