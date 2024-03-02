package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.deal.recension.RecensionDTO;
import hr.fer.unifier.backend.api.deal.recension.RecensionRequestDTO;

import java.util.List;

public interface RecensionService {
    void addRecension(RecensionRequestDTO recensionRequestDTO);

    List<RecensionDTO> getUserRecensions(Long userId);
}
