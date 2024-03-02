package hr.fer.unifier.backend.api.deal;

import hr.fer.unifier.backend.enums.Sender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealDTO {

    private Long senderId;

    private Sender sender;

    private Long requestId;

    private Long advertId;

    private String message;
}
