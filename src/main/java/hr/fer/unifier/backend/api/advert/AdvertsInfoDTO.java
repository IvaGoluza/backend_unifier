package hr.fer.unifier.backend.api.advert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertsInfoDTO {
    private List<AdvertInfoDTO> active;
    private List<AdvertInfoDTO> archived;
}
