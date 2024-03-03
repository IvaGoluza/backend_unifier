package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.user.profile.gallery.GalleryDTO;
import hr.fer.unifier.backend.api.user.profile.gallery.GalleryRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface GalleryService {
    void saveToGallery(GalleryRequestDTO galleryRequestDTO, MultipartFile file);
    Page<GalleryDTO> getGallery(Long userId, Pageable pageable);
}
