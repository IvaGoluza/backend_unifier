package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.api.request.RequestsInfoDTO;
import hr.fer.unifier.backend.util.pagination.UnifierPage;
import org.springframework.data.domain.Pageable;

public interface RequestService {
  RequestResponseDTO saveRequest(RequestDTO requestDTO);
  void archive(Long id);
  void undoArchive(Long id);
  UnifierPage<RequestResponseDTO> getAllRequests(String city, String category, String helpType, Pageable pageable);
  RequestResponseDTO getRequest(Long userId, Long requestId);
  UnifierPage<RequestsInfoDTO> getRequestInfo(Long userId, Pageable pageable);
}
