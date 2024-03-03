package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.api.request.RequestsInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {
  RequestResponseDTO saveRequest(RequestDTO requestDTO);
  void changeDeleteStatus(Long id);
  RequestResponseDTO getRequest(Long userId, Long requestId);
  Page<RequestResponseDTO> getAllRequests(Pageable pageable);

  RequestsInfoDTO getRequestInfo(Long userId);
}
