package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.deal.*;
import hr.fer.unifier.backend.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/deal")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class DealResource {

  private final DealService dealService;

  @PostMapping
  public ResponseEntity<DealResponseDTO> saveDeal(@RequestBody DealDTO dealDTO) {
    return ResponseEntity.ok(dealService.saveDeal(dealDTO));
  }

  @GetMapping("/all-deals/{requestId}")
  public ResponseEntity<List<DealResponseDTO>> getDeals(@PathVariable Long requestId) {
    return ResponseEntity.ok(dealService.getDeals(requestId));
  }

  @PutMapping("/accepted/{dealId}")
  public void updateAccepted(@PathVariable Long dealId) {
    dealService.updateAccepted(dealId);
  }

  @DeleteMapping("/{dealId}")
  public void deleteDeal(@PathVariable Long dealId) {
    dealService.deleteDeal(dealId);
  }

  @PutMapping("/recension")
  public void updateRecension(@RequestBody RecensionDTO recensionDTO) {
    dealService.updateRecension(recensionDTO);
  }

  @PutMapping("/note")
  public void updateNote(@RequestBody NoteDTO noteDTO) {
    dealService.updateNote(noteDTO);
  }

  @GetMapping("/help-requests/{userId}")
  public ResponseEntity<List<DealResponseDTO>> getHelpDealsForVol(@PathVariable Long userId) {
    return ResponseEntity.ok(dealService.getHelpRequestsDeals(userId));
  }

  @GetMapping("/my-deals-requests/{userId}")
  public ResponseEntity<List<DealRequestDTO>> getDealsRequests(@PathVariable Long userId) {
    return ResponseEntity.ok(dealService.getRequestDeals(userId));
  }

  @GetMapping("/my-deals-adverts/{userId}")
  public ResponseEntity<List<DealAdvertDTO>> getDealsAdverts(@PathVariable Long userId) {
    return ResponseEntity.ok(dealService.getAdvertDeals(userId));
  }

}
