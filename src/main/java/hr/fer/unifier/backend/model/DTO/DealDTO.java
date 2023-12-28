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
public class DealDTO {

    private Long id;

    private Sender sender;

    private Long requestId;

    private Long advertId;

}
