package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.deal.DealDTO;
import hr.fer.unifier.backend.api.deal.DealResponseDTO;
import hr.fer.unifier.backend.api.deal.PersonInNeedApplicationDTO;
import hr.fer.unifier.backend.api.deal.VolunteerHelpApplicationDTO;
import hr.fer.unifier.backend.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/deal", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class DealResource {

  private final DealService dealService;

  @PostMapping
  public ResponseEntity<DealResponseDTO> saveDeal(@RequestBody DealDTO dealDTO) {
    return ResponseEntity.ok(dealService.saveDeal(dealDTO));
  }
  @PutMapping("/accepted/{dealId}")
  public void updateAccepted(@PathVariable Long dealId) {
    dealService.updateAccepted(dealId);
  }

  @DeleteMapping("/{dealId}")
  public void deleteDeal(@PathVariable Long dealId) {
    dealService.deleteDeal(dealId);
  }

  @GetMapping("/{requestId}/volunteer-applications")
  public List<VolunteerHelpApplicationDTO> getVolunteerApplications(@PathVariable Long requestId){
    return dealService.getVolunteersHelpApplications(requestId);
  }

  @GetMapping("/{advertId}/person-in-need-applications")
  public List<PersonInNeedApplicationDTO> getPersonInNeedApplications(@PathVariable Long advertId){
    return dealService.getPersonInNeedApplications(advertId);
  }


}
