package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.advert.AdvertDTO;
import hr.fer.unifier.backend.api.advert.AdvertImageDTO;
import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import hr.fer.unifier.backend.api.advert.AdvertsInfoDTO;
import hr.fer.unifier.backend.util.pagination.UnifierPage;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AdvertService {

  AdvertResponseDTO saveAdvert(AdvertDTO advertDTO, MultipartFile file);
  AdvertResponseDTO saveAdvert(AdvertDTO advertDTO);
  void changeDeleteStatus(Long id);
  UnifierPage<AdvertResponseDTO> getAllAdverts(String city, String category, String helpType, Pageable pageable);
  AdvertResponseDTO getAdvert(Long userId, Long advertId);
  UnifierPage<AdvertsInfoDTO> getAdvertsInfo(Long userId, Pageable pageable);

  AdvertImageDTO getAdvertImageDTO(Long advertId);

  void removeHelperVolunteer(Long advertId, Long userId);

  void addHelperVolunteer(Long advertId, Long userId);
}
