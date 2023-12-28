package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.model.DTO.RequestDTO;
import hr.fer.unifier.backend.model.DTO.RequestResponseDTO;

import java.util.List;

public interface RequestService {
  RequestResponseDTO saveRequest(RequestDTO requestDTO);
  void deleteRequest(Long id);
  List<RequestResponseDTO> getRequests(Long userId);
  List<RequestResponseDTO> getAllRequests();

}
