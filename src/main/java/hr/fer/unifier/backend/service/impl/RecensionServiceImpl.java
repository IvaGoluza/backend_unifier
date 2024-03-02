package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.deal.recension.RecensionRequestDTO;
import hr.fer.unifier.backend.db.DealDao;
import hr.fer.unifier.backend.db.RecensionDao;
import hr.fer.unifier.backend.db.entity.Deal;
import hr.fer.unifier.backend.db.entity.Recension;
import hr.fer.unifier.backend.service.RecensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RecensionServiceImpl implements RecensionService {
    private final RecensionDao recensionDao;
    private final DealDao dealDao;
    @Transactional
    @Override
    public void addRecension(RecensionRequestDTO recensionRequestDTO) {
        final Deal deal = dealDao.findById(recensionRequestDTO.getDealId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Deal with id = %d doesn't exists", recensionRequestDTO.getDealId()))
        );

        final Recension recension = new Recension();
        recension.setRecension(recensionRequestDTO.getRecension());
        recension.setDeal(deal);

        recensionDao.save(recension);
    }
}
