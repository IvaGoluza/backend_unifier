package hr.fer.unifier.backend.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestsInfoDTO {
    private List<RequestInfoDTO> active;
    private List<RequestInfoDTO> archived;
}
