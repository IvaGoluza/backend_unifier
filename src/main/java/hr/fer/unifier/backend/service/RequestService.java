package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequestService {
  RequestResponseDTO saveRequest(RequestDTO requestDTO);
  void changeDeleteStatus(Long id);
  List<RequestResponseDTO> getRequests(Long userId);
  Page<RequestResponseDTO> getAllRequests(Pageable pageable);

}
