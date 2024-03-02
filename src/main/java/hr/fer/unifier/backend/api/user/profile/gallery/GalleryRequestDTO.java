package hr.fer.unifier.backend.api.user.profile.gallery;

import lombok.Data;
import lombok.NonNull;

@Data
public class GalleryRequestDTO {
    @NonNull
    private String message;

    @NonNull
    private Long userId;
}
