package hr.fer.unifier.backend.api.advert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertsInfoDTO {
    private Long advertId;

    private String advertTitle;

    private String category;

    private String helpType;

    private boolean archived;
}
