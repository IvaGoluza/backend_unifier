package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.deal.*;
import hr.fer.unifier.backend.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
  public Page<VolunteerHelpApplicationDTO> getVolunteerApplications(@PathVariable Long requestId, @ParameterObject Pageable pageable){
    return dealService.getVolunteersHelpApplications(requestId,pageable);
  }

  @GetMapping("/{advertId}/person-in-need-applications")
  public Page<PersonInNeedApplicationDTO> getPersonInNeedApplications(@PathVariable Long advertId, @ParameterObject Pageable pageable){
    return dealService.getPersonInNeedApplications(advertId,pageable);
  }

  @GetMapping("/accepted-deals-person-in-need/{userId}")
  public Page<AcceptedPersonInNeedDealsDTO> getAcceptedDealsPersonInNeed(@PathVariable Long userId, @ParameterObject Pageable pageable){
    return dealService.getAcceptedDealsForPersonInNeed(userId,pageable);
  }

  @GetMapping("/accepted-deals-volunteer/{userId}")
  public Page<AcceptedDealsVolunteerDTO> getAcceptedDealsVolunteer(@PathVariable Long userId, @ParameterObject Pageable pageable){
    return dealService.getAcceptedDealsForVolunteer(userId,pageable);
  }

  @PutMapping("/volunteer-description/{dealId}")
  public void updateVolunteerDealDescription(@PathVariable Long dealId, @RequestBody VolunteerDealDescriptionDTO volunteerDealDescriptionDTO){
    dealService.updateVolunteerDealDescription(dealId,volunteerDealDescriptionDTO);
  }
}
