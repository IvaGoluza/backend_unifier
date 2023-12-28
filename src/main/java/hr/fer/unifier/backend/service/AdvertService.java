package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.model.DTO.AdvertDTO;
import hr.fer.unifier.backend.model.DTO.AdvertResponseDTO;
import hr.fer.unifier.backend.model.enums.Category;
import hr.fer.unifier.backend.model.enums.HelpType;
import hr.fer.unifier.backend.model.enums.Town;

import java.util.List;

public interface AdvertService {

  AdvertResponseDTO saveAdvert(AdvertDTO advertDTO);

  void deleteAdvert(Long id);

  List<AdvertResponseDTO> getAdverts(Long userId);

  List<AdvertResponseDTO> getAllAdverts();

}
