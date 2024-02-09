package hr.fer.unifier.backend.api.deal;

import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealRequestDTO {

    private Long id;

    private boolean accepted;

    private RequestResponseDTO request;

}
