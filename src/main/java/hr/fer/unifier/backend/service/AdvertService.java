package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.advert.*;
import hr.fer.unifier.backend.api.request.RequestsTitlesDTO;
import hr.fer.unifier.backend.util.pagination.UnifierPage;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.sql.SQLException;

public interface AdvertService {

    AdvertResponseDTO saveAdvert(AdvertDTO advertDTO, MultipartFile file);

    AdvertResponseDTO saveAdvert(AdvertDTO advertDTO);

    void changeDeleteStatus(Long id);

    UnifierPage<AdvertResponseDTO> getAllAdverts(String city, String category, String helpType, Long userId, Pageable pageable);

    MyAdvertResponse getAdvert(Long userId, Long advertId);

    UnifierPage<AdvertsInfoDTO> getAdvertsInfo(Long userId, Pageable pageable);

    ResponseEntity<StreamingResponseBody> getAdvertImageDTO(Long advertId) throws SQLException;

    void removeHelperVolunteer(Long advertId, Long userId);

    void addHelperVolunteer(Long advertId, Long userId);

    UnifierPage<AdvertsTitlesDTO> getAdvertTitles(Long userId, Pageable pageable);

    void undoArchive(Long id);
}
