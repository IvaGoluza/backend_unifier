package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.deal.recension.RecensionRequestDTO;

public interface RecensionService {
    void addRecension(RecensionRequestDTO recensionRequestDTO);
}
