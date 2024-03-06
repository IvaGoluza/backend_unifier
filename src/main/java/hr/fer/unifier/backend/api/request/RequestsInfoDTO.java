package hr.fer.unifier.backend.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestsInfoDTO {
    private Long requestId;

    private String requestTitle;

    private String category;

    private String helpType;

    private boolean archived;
}
