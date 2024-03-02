package hr.fer.unifier.backend.api.deal.recension;

import lombok.Data;
import lombok.NonNull;

@Data
public class RecensionRequestDTO {
    @NonNull
    private String recension;
    @NonNull
    private Long dealId;
    @NonNull
    private Long reviewingUserId;
}
