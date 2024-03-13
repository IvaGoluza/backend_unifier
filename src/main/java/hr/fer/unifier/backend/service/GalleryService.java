package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.user.profile.gallery.GalleryDTO;
import hr.fer.unifier.backend.api.user.profile.gallery.GalleryRequestDTO;
import hr.fer.unifier.backend.util.pagination.UnifierPage;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface GalleryService {
    void saveToGallery(GalleryRequestDTO galleryRequestDTO, MultipartFile file);
    UnifierPage<GalleryDTO> getGallery(Long userId, Pageable pageable);
}
