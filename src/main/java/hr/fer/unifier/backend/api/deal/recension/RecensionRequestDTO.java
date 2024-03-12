package hr.fer.unifier.backend.api.deal.recension;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecensionRequestDTO {
    @NonNull
    private String recension;

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate endDate;

    @NonNull
    private Long dealId;

    @NonNull
    private Long reviewingUserId;
}
