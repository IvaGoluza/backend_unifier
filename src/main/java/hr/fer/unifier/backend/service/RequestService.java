package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;

import java.util.List;

public interface RequestService {
  RequestResponseDTO saveRequest(RequestDTO requestDTO);
  void changeDeleteStatus(Long id);
  List<RequestResponseDTO> getRequests(Long userId);
  List<RequestResponseDTO> getAllRequests();

}
