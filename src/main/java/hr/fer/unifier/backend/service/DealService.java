package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.deal.DealDTO;
import hr.fer.unifier.backend.api.deal.DealResponseDTO;
import hr.fer.unifier.backend.api.deal.PersonInNeedApplicationDTO;
import hr.fer.unifier.backend.api.deal.VolunteerHelpApplicationDTO;

import java.util.List;

public interface DealService {

  DealResponseDTO saveDeal(DealDTO dealDTO);
  void updateAccepted(Long dealId);
  void deleteDeal(Long dealId);
  List<VolunteerHelpApplicationDTO> getVolunteersHelpApplications(Long requestId);
  List<PersonInNeedApplicationDTO> getPersonInNeedApplications(Long advertId);
}
