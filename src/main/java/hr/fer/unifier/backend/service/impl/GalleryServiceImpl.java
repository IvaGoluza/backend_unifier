package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.user.profile.gallery.GalleryDTO;
import hr.fer.unifier.backend.api.user.profile.gallery.GalleryRequestDTO;
import hr.fer.unifier.backend.db.GalleryDao;
import hr.fer.unifier.backend.db.entity.Gallery;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.mapper.GalleryMapper;
import hr.fer.unifier.backend.service.GalleryService;
import hr.fer.unifier.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static hr.fer.unifier.backend.util.FileUtil.validateImage;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {
    private final GalleryMapper galleryMapper;
    private final GalleryDao galleryDao;
    private final UserService userService;
    @Transactional
    @Override
    public void saveToGallery(GalleryRequestDTO galleryReq, MultipartFile file) {
        validateImage(file);
        final Gallery gallery = new Gallery();
        gallery.setDescription(gallery.getDescription());
        gallery.setUser(userService.getUserById(galleryReq.getUserId()));
        try {
            gallery.setImage(file.getBytes());
        }catch (IOException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't upload image!", ex);
        }

        galleryDao.save(gallery);
    }

    @Transactional
    @Override
    public List<GalleryDTO> getGallery(Long userId) {
        final User user = userService.getUserById(userId);
        return galleryDao.findAllByUser(user)
                .orElse(Collections.emptyList())
                .stream()
                .map(galleryMapper::toGalleryDTO)
                .toList();

    }
}
