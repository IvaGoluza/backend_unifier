package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.advert.AdvertDTO;
import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;

import java.util.List;

public interface AdvertService {

  AdvertResponseDTO saveAdvert(AdvertDTO advertDTO);

  void deleteAdvert(Long id);

  List<AdvertResponseDTO> getAdverts(Long userId);

  List<AdvertResponseDTO> getAllAdverts();

}
