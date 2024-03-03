package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.advert.AdvertDTO;
import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import hr.fer.unifier.backend.api.advert.AdvertsInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AdvertService {

  AdvertResponseDTO saveAdvert(AdvertDTO advertDTO, MultipartFile file);
  AdvertResponseDTO saveAdvert(AdvertDTO advertDTO);

  void changeDeleteStatus(Long id);
  Page<AdvertResponseDTO> getAllAdverts(Pageable pageable);
  AdvertResponseDTO getAdvert(Long userId, Long advertId);
  AdvertsInfoDTO getAdvertsInfo(Long userId);

}
