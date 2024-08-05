package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.user.profile.gallery.GalleryDTO;
import hr.fer.unifier.backend.api.user.profile.gallery.GalleryRequestDTO;
import hr.fer.unifier.backend.config.core.UserLocalThread;
import hr.fer.unifier.backend.db.GalleryDao;
import hr.fer.unifier.backend.db.entity.Gallery;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.mapper.GalleryMapper;
import hr.fer.unifier.backend.service.GalleryService;
import hr.fer.unifier.backend.service.UserService;
import hr.fer.unifier.backend.util.file.StreamingUtil;
import hr.fer.unifier.backend.util.pagination.PageUtil;
import hr.fer.unifier.backend.util.pagination.UnifierPage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import static hr.fer.unifier.backend.util.file.FileUtil.validateImage;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {
    private final GalleryMapper galleryMapper;
    private final GalleryDao galleryDao;
    private final StreamingUtil streamingUtil;
    private final UserService userService;

    @Transactional
    @Override
    public void saveToGallery(GalleryRequestDTO galleryReq, MultipartFile file) {
        validateImage(file);
        final Gallery gallery = new Gallery();
        gallery.setDescription(galleryReq.getDescription());
        gallery.setUser(userService.getUserById(galleryReq.getUserId()));
        try {
            gallery.setImage(file.getBytes());
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't upload image!", ex);
        }

        galleryDao.save(gallery);
    }

    @Transactional(readOnly = true)
    @Override
    public UnifierPage<GalleryDTO> getGallery(Long userId, Pageable pageable) {
        final User user = userService.getUserById(userId);
        return PageUtil.map(galleryDao.findAllByUser(user, pageable), galleryMapper::toGalleryDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<StreamingResponseBody> getImage(Long galleryId) throws SQLException {
        final Gallery gallery = galleryDao.findById(galleryId).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find image with gallery id = " + galleryId)
        );

        final String fileName = String.format("gallery_%d.jpg", galleryId);
        final Blob blob = new SerialBlob(gallery.getImage());
        return streamingUtil.getBlobStreamingResponse(fileName, blob);
    }
}
