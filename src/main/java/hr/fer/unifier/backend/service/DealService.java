package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.deal.*;

import java.util.List;

public interface DealService {

  DealResponseDTO saveDeal(DealDTO dealDTO);
  List<DealResponseDTO> getHelpRequestsDeals(Long userId);
  void updateAccepted(Long dealId);
  void deleteDeal(Long dealId);
  List<DealRequestDTO> getRequestDeals(Long userId);
  List<DealAdvertDTO> getAdvertDeals(Long userId);
  void updateRecension(RecensionDTO recensionDTO);
  void updateNote(NoteDTO noteDTO);
  List<DealResponseDTO> getDeals(Long requestId);
}
