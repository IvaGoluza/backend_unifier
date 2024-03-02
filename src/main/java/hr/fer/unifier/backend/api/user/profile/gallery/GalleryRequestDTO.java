package hr.fer.unifier.backend.api.user.profile.gallery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GalleryRequestDTO {
    @NonNull
    private String description;

    @NonNull
    private Long userId;
}
