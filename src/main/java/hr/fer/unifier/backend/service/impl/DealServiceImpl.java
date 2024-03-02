package hr.fer.unifier.backend.service.impl;


import hr.fer.unifier.backend.api.deal.DealDTO;
import hr.fer.unifier.backend.api.deal.DealResponseDTO;
import hr.fer.unifier.backend.api.deal.PersonInNeedApplicationDTO;
import hr.fer.unifier.backend.api.deal.VolunteerHelpApplicationDTO;
import hr.fer.unifier.backend.db.AdvertDao;
import hr.fer.unifier.backend.db.DealDao;
import hr.fer.unifier.backend.db.RequestDao;
import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.entity.Deal;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.Sender;
import hr.fer.unifier.backend.mapper.DealMapper;
import hr.fer.unifier.backend.service.DealService;
import hr.fer.unifier.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

  private final DealDao dealDao;
  private final UserService userService;
  private final DealMapper dealMapper;
  private final RequestDao requestDao;
  private final AdvertDao advertDao;

  @Transactional
  @Override
  public DealResponseDTO saveDeal(final DealDTO dealDTO) {
    validateDealRequest(dealDTO);
    final Request request = dealDTO.getRequestId() != null ? requestDao.findById(dealDTO.getRequestId()).orElseThrow(() ->
            new EntityNotFoundException("Request with id " + dealDTO.getRequestId() + " does not exist.")
    ) : null;
    final Advert advert = dealDTO.getAdvertId() != null ? advertDao.findById(dealDTO.getAdvertId()).orElseThrow(() ->
            new EntityNotFoundException("Advert with id " + dealDTO.getAdvertId() + " does not exist.")
    ) : null;
    final User sender = userService.getUserById(dealDTO.getSenderId());

    final Deal deal = dealDao.save(dealMapper.toDeal(dealDTO));
    deal.setRequest(request);
    deal.setAdvert(advert);
    deal.setSenderId(sender);

    return dealMapper.toDealResponseDTO(deal);
  }


  @Transactional
  @Override
  public void updateAccepted(Long dealId) {
    dealDao.findById(dealId).orElseThrow(() ->
       new EntityNotFoundException("Deal with id " + dealId + " does not exist.")
    );

    final Deal deal = dealDao.findById(dealId).get();

    if (deal.isAccepted()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,"Deal is already accepted");
    }

    deal.setAccepted(true);

    final Request request = deal.getRequest();
    int newVolunteerNumber = request.getNumOfVolunteers() - 1;
    if(newVolunteerNumber == 0) {
      request.setActive(false);
      dealDao.deleteByAcceptedFalseAndRequest(request);
    }
    request.setNumOfVolunteers(newVolunteerNumber);
  }

  @Transactional
  @Override
  public void deleteDeal(Long dealId) {
    final Deal deal = dealDao.findById(dealId).orElseThrow(() ->
       new EntityNotFoundException("Deal with id " + dealId + " does not exist.")
    );

    dealDao.delete(deal);
  }

  @Transactional
  @Override
  public List<VolunteerHelpApplicationDTO> getVolunteersHelpApplications(Long requestId) {
    final Request request = requestDao.findById(requestId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Ne postoji zahtjev s id = %d", requestId))
    );
    if (!request.getActive()){
      return null;
    }

    return dealDao.findAllByRequestAndSender(request, Sender.VOLUNTEER)
            .orElse(Collections.emptyList())
            .stream()
            .map(this::createVolunteerHelpApplications)
            .toList();
  }

  @Transactional
  @Override
  public List<PersonInNeedApplicationDTO> getPersonInNeedApplications(Long advertId) {
    final Advert advert = advertDao.findById(advertId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Ne postoji volonterski oglas s id = %d", advertId))
    );

    return dealDao.findAllByAdvertAndSender(advert, Sender.PERSON_IN_NEED)
            .orElse(Collections.emptyList())
            .stream()
            .map(this::createPersonInNeedApplicationDTO)
            .toList();
  }

  private void validateDealRequest(DealDTO dealDTO) {
    if (dealDTO.getSender() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nedostaje sender!");
    }

    if (dealDTO.getSender().equals(Sender.PERSON_IN_NEED)){
      if (dealDTO.getAdvertId() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nedostaje advert id!");
      }

      if (dealDTO.getMessage() == null && dealDTO.getRequestId() == null){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Potrebno je priložit ili zahtjev ili poruku!");
      }
    }else {
      if (dealDTO.getRequestId() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nedostaje advert id!");
      }

      if (dealDTO.getMessage() == null && dealDTO.getAdvertId() == null){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Potrebno je priložit ili oglas ili poruku!");
      }
    }
  }

  private PersonInNeedApplicationDTO createPersonInNeedApplicationDTO(Deal deal) {
    final PersonInNeedApplicationDTO personInNeedApplicationDTO = dealMapper.toPersonInNeedApplicationDTO(deal);

    personInNeedApplicationDTO.setUser(userService.getUserCardInfo(deal.getSenderId().getId()));

    return personInNeedApplicationDTO;
  }

  private VolunteerHelpApplicationDTO createVolunteerHelpApplications(Deal deal) {
    final VolunteerHelpApplicationDTO volunteerHelpApplicationDTO = dealMapper.toVolunteerHelpApplicationDTO(deal);

    volunteerHelpApplicationDTO.setUser(userService.getUserCardInfo(deal.getSenderId().getId()));

    return volunteerHelpApplicationDTO;
  }
}
