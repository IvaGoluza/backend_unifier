package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestsInfoDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class RequestResource {

  private final RequestService requestService;

  @PostMapping
  public ResponseEntity<RequestResponseDTO> saveRequest(@RequestBody RequestDTO requestDTO) {
    return ResponseEntity.ok(requestService.saveRequest(requestDTO));
  }

  @PutMapping("/change-delete-status/{id}")
  public void deleteRequest(@PathVariable Long id) {
    requestService.changeDeleteStatus(id);
  }

  @GetMapping("/{requestId}/my-request/{userId}")
  public ResponseEntity<RequestResponseDTO> getRequests(@PathVariable Long userId, @PathVariable Long requestId) {
    return ResponseEntity.ok(requestService.getRequest(userId,requestId));
  }

  @GetMapping("/my-requests-info/{userId}")
  public ResponseEntity<RequestsInfoDTO> getRequests(@PathVariable Long userId) {
    return ResponseEntity.ok(requestService.getRequestInfo(userId));
  }

  @GetMapping(value = {"/all-requests"})
  public ResponseEntity<Page<RequestResponseDTO>> getAllRequests(@ParameterObject Pageable pageable) {
    return ResponseEntity.ok(requestService.getAllRequests(pageable));
  }

}