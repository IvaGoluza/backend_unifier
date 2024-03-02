package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.user.profile.gallery.GalleryDTO;
import hr.fer.unifier.backend.api.user.profile.gallery.GalleryRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GalleryService {
    void saveToGallery(GalleryRequestDTO galleryRequestDTO, MultipartFile file);
    List<GalleryDTO> getGallery(Long userId);
}
