package hr.fer.unifier.backend.mapper;

import hr.fer.unifier.backend.api.user.profile.gallery.GalleryDTO;
import hr.fer.unifier.backend.db.entity.Gallery;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CommonMapper.class})
public interface GalleryMapper {

    GalleryDTO toGalleryDTO(Gallery gallery);
}
