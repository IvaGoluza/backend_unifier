package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class RequestResource {

  private final RequestService requestService;

  @PostMapping("/my-requests")
  public ResponseEntity<RequestResponseDTO> saveRequest(@RequestBody RequestDTO requestDTO) {
    return ResponseEntity.ok(requestService.saveRequest(requestDTO));
  }

  @PutMapping("/my-requests/{id}")
  public void deleteRequest(@PathVariable Long id) {
    requestService.deleteRequest(id);
  }

  @GetMapping("/my-requests/{userId}")
  public ResponseEntity<List<RequestResponseDTO>> getRequests(@PathVariable Long userId) {
    return ResponseEntity.ok(requestService.getRequests(userId));
  }

  @GetMapping(value = {"/opportunities"})
  public ResponseEntity<List<RequestResponseDTO>> getAllRequests() {
    return ResponseEntity.ok(requestService.getAllRequests());
  }

}