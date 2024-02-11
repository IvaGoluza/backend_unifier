package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/request")
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

  @GetMapping("/my-requests/{userId}")
  public ResponseEntity<List<RequestResponseDTO>> getRequests(@PathVariable Long userId) {
    return ResponseEntity.ok(requestService.getRequests(userId));
  }

  @GetMapping(value = {"/all-requests"})
  public ResponseEntity<List<RequestResponseDTO>> getAllRequests() {
    return ResponseEntity.ok(requestService.getAllRequests());
  }

}