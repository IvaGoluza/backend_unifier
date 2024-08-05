package hr.fer.unifier.backend.mapper;

import hr.fer.unifier.backend.api.user.profile.gallery.GalleryDTO;
import hr.fer.unifier.backend.db.entity.Gallery;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CommonMapper.class})
public interface GalleryMapper {

    default GalleryDTO toGalleryDTO(Gallery gallery) {
        final GalleryDTO galleryDTO = new GalleryDTO();
        if (gallery.getImage() != null) {
            final String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/profile/user-gallery/image/")
                    .path(gallery.getGalleryId().toString())
                    .toUriString();
            galleryDTO.setImageUrl(imageUrl);
        }
        galleryDTO.setDescription(galleryDTO.getDescription());
        return galleryDTO;
    }
}
