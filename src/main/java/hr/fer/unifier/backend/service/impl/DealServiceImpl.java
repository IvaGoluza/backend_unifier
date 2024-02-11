package hr.fer.unifier.backend.service.impl;


import hr.fer.unifier.backend.api.deal.*;
import hr.fer.unifier.backend.db.AdvertDao;
import hr.fer.unifier.backend.db.DealDao;
import hr.fer.unifier.backend.db.RequestDao;
import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.entity.Deal;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.Sender;
import hr.fer.unifier.backend.service.DealService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
  private final UserDao userDao;
  private final ModelMapper modelMapper;
  private final RequestDao requestDao;
  private final AdvertDao advertDao;

  @Transactional
  @Override
  public DealResponseDTO saveDeal(final DealDTO dealDTO) {
    Deal deal = modelMapper.map(dealDTO, Deal.class);

    deal.setAccepted(false);

    final Request request = requestDao.findById(dealDTO.getRequestId()).orElseThrow(() ->
            new EntityNotFoundException("Request with id " + dealDTO.getRequestId() + " does not exist.")
    );
    deal.setRequest(request);
    final Advert advert = advertDao.findById(dealDTO.getAdvertId()).orElseThrow(() ->
            new EntityNotFoundException("Advert with id " + dealDTO.getAdvertId() + " does not exist.")
    );
    deal.setAdvert(advert);
    deal = dealDao.save(deal);

    return modelMapper.map(deal, DealResponseDTO.class);
  }

  @Transactional(readOnly = true)
  @Override
  public List<DealResponseDTO> getHelpRequestsDeals(Long userId) {
    final User user = userDao.findById(userId).orElseThrow(() ->
            new EntityNotFoundException("User with id " + userId + " does not exist.")
    );

    return dealDao.findByAcceptedFalseAndAdvert_UserAndSender(user, Sender.REQUEST)
            .orElse(Collections.emptyList())
            .stream()
            .map(deal -> modelMapper.map(deal, DealResponseDTO.class))
            .toList();
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
    int newVolunteerNumber = request.getVolunteerNum() - 1;
    if(newVolunteerNumber == 0) {
      request.setActive(false);
      dealDao.deleteByAcceptedFalseAndRequest(request);
    }
    request.setVolunteerNum(newVolunteerNumber);
  }

  @Transactional
  @Override
  public void deleteDeal(Long dealId) {
    final Deal deal = dealDao.findById(dealId).orElseThrow(() ->
       new EntityNotFoundException("Deal with id " + dealId + " does not exist.")
    );

    dealDao.delete(deal);
  }

  @Transactional(readOnly = true)
  @Override
  public List<DealRequestDTO> getRequestDeals(Long userId) {
    final User user = userDao.findById(userId).orElseThrow(() ->
            new EntityNotFoundException("User with id " + userId + " does not exist.")
    );

    return dealDao.findByAdvert_UserAndSenderAndAcceptedTrueOrSenderAndAdvert_User(user, Sender.REQUEST, Sender.ADVERT, user)
            .orElse(Collections.emptyList())
            .stream()
            .map(deal -> modelMapper.map(deal, DealRequestDTO.class))
            .toList();
  }

  @Override
  public List<DealAdvertDTO> getAdvertDeals(Long userId) {
    User user = userDao.findById(userId).orElseThrow(() ->
            new EntityNotFoundException("User with id " + userId + " does not exist.")
    );

    return dealDao.findByRequest_UserAndSenderAndAcceptedTrueOrSenderAndRequest_User(user, Sender.ADVERT, Sender.REQUEST, user)
            .orElse(Collections.emptyList())
            .stream()
            .map(deal -> modelMapper.map(deal, DealAdvertDTO.class))
            .toList();
  }

  @Transactional
  @Override
  public void updateRecension(RecensionDTO recensionDTO) {
    final Deal deal = dealDao.findById(recensionDTO.getId()).orElseThrow(
            () -> new EntityNotFoundException("Deal with id " + recensionDTO.getId() + " does not exist.")
    );

    deal.setRecension(recensionDTO.getRecension());
  }

  @Transactional
  @Override
  public void updateNote(NoteDTO noteDTO) {
    final Deal deal = dealDao.findById(noteDTO.getId()).orElseThrow(
            () -> new EntityNotFoundException("Deal with id " + noteDTO.getId() + " does not exist.")
    );

    deal.setNote(noteDTO.getNote());
  }

  @Override
  public List<DealResponseDTO> getDeals(Long requestId) {
    final Request request = requestDao.findById(requestId).orElseThrow(
            () -> new EntityNotFoundException("Request with id "+ requestId + " does not exist")
    );
    return dealDao.findByAcceptedFalseAndRequestAndSender(request, Sender.ADVERT)
            .orElse(Collections.emptyList())
            .stream()
            .map(deal -> modelMapper.map(deal, DealResponseDTO.class))
            .toList();

  }
}
