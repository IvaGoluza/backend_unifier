package hr.fer.unifier.backend.controller;

import hr.fer.unifier.backend.model.DTO.RequestDTO;
import hr.fer.unifier.backend.model.DTO.RequestResponseDTO;
import hr.fer.unifier.backend.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:3000")
public class RequestController {

  private final RequestService requestService;

  @Autowired
  public RequestController(RequestService requestService) {
    this.requestService = requestService;
  }

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