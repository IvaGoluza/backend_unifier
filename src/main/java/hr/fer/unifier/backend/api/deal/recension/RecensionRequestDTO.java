package hr.fer.unifier.backend.api.deal.recension;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecensionRequestDTO {
    @NonNull
    private String recension;
    @NonNull
    private Long dealId;
    @NonNull
    private Long reviewingUserId;
}
