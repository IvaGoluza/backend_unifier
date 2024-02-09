package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.deal.*;
import hr.fer.unifier.backend.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class DealResource {

  private final DealService dealService;

  @Autowired
  public DealResource(DealService dealService) {
    this.dealService = dealService;
  }

  @PostMapping("/deals")
  public ResponseEntity<DealResponseDTO> saveDeal(@RequestBody DealDTO dealDTO) {
    return ResponseEntity.ok(dealService.saveDeal(dealDTO));
  }

  @GetMapping("/deals/{requestId}")
  public ResponseEntity<List<DealResponseDTO>> getDeals(@PathVariable Long requestId) {
    return ResponseEntity.ok(dealService.getDeals(requestId));
  }

  @PutMapping("/deals/{dealId}")
  public void updateAccepted(@PathVariable Long dealId) {
    dealService.updateAccepted(dealId);
  }

  @DeleteMapping("/deals/{dealId}")
  public void deleteDeal(@PathVariable Long dealId) {
    dealService.deleteDeal(dealId);
  }

  @PutMapping("/deals/recension")
  public void updateRecension(@RequestBody RecensionDTO recensionDTO) {
    dealService.updateRecension(recensionDTO);
  }

  @PutMapping("/deals/note")
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

  @GetMapping("/profile/recension/{userId}")
  public ResponseEntity<List<RecensionResponseDTO>> getRecensions(@PathVariable Long userId) {
    return ResponseEntity.ok(dealService.getRecensions(userId));
  }

  @GetMapping("/profile/note/{userId}")
  public ResponseEntity<List<NoteResponseDTO>> getNotes(@PathVariable Long userId) {
    return ResponseEntity.ok(dealService.getNotes(userId));
  }

}
