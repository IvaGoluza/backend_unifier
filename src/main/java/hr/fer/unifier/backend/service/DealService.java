package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.deal.DealDTO;
import hr.fer.unifier.backend.api.deal.DealResponseDTO;
import hr.fer.unifier.backend.api.deal.PersonInNeedApplicationDTO;
import hr.fer.unifier.backend.api.deal.VolunteerHelpApplicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DealService {

  DealResponseDTO saveDeal(DealDTO dealDTO);
  void updateAccepted(Long dealId);
  void deleteDeal(Long dealId);
  Page<VolunteerHelpApplicationDTO> getVolunteersHelpApplications(Long requestId, Pageable pageable);
  Page<PersonInNeedApplicationDTO> getPersonInNeedApplications(Long advertId, Pageable pageable);
}
