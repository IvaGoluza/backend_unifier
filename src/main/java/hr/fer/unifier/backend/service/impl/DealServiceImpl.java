package hr.fer.unifier.backend.service.impl;


import hr.fer.unifier.backend.model.Advert;
import hr.fer.unifier.backend.model.DTO.*;
import hr.fer.unifier.backend.model.Deal;
import hr.fer.unifier.backend.model.Request;
import hr.fer.unifier.backend.model.enums.Sender;
import hr.fer.unifier.backend.repository.AdvertRepository;
import hr.fer.unifier.backend.repository.DealRepository;
import hr.fer.unifier.backend.repository.RequestRepository;
import hr.fer.unifier.backend.repository.UserRepository;
import hr.fer.unifier.backend.service.DealService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealServiceImpl implements DealService {

  private final DealRepository dealRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final RequestRepository requestRepository;
  private final AdvertRepository advertRepository;

  @Autowired
  public DealServiceImpl(DealRepository dealRepository, UserRepository userRepository, ModelMapper modelMapper, RequestRepository requestRepository, AdvertRepository advertRepository) {
    this.dealRepository = dealRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.requestRepository = requestRepository;
    this.advertRepository = advertRepository;
  }

  @Override
  public DealResponseDTO saveDeal(DealDTO dealDTO) {
    Deal deal = modelMapper.map(dealDTO, Deal.class);

    deal.setAccepted(false);

    Request request = requestRepository.findById(dealDTO.getRequestId()).orElseThrow(() ->
            new EntityNotFoundException("Request with id " + dealDTO.getRequestId() + " does not exist.")
    );
    deal.setRequest(request);
    /*
    List<Deal> requestDeals = request.getDeals();
    requestDeals.add(deal);
    request.setDeals(requestDeals);
    requestRepository.save(request);
    */
    Advert advert = advertRepository.findById(dealDTO.getAdvertId()).orElseThrow(() ->
            new EntityNotFoundException("Advert with id " + dealDTO.getAdvertId() + " does not exist.")
    );
    deal.setAdvert(advert);
    /*
    List<Deal> advertDeals = advert.getDeals();
    advertDeals.add(deal);
    advert.setDeals(advertDeals);
    advertRepository.save(advert);
    */
    deal = dealRepository.save(deal);

    return modelMapper.map(deal, DealResponseDTO.class);
  }

  @Override
  public List<DealResponseDTO> getHelpRequestsDeals(Long userId) {
    userRepository.findById(userId).orElseThrow(() ->
            new EntityNotFoundException("User with id " + userId + " does not exist.")
    );
    List<Deal> deals = dealRepository.findDealsByAcceptedFalseAndAdvert_User_IdAndSender(userId, Sender.REQUEST);

    return deals.stream().map(deal -> modelMapper.map(deal, DealResponseDTO.class)).toList();
  }

  @Override
  public void updateAccepted(Long dealId) {
    dealRepository.findById(dealId).orElseThrow(() -> {
      throw new EntityNotFoundException("Deal with id " + dealId + " does not exist.");
    });

    Deal deal = dealRepository.findById(dealId).get();

    if (deal.isAccepted()) {
      throw new IllegalStateException("Deal is already accepted");
    }
    deal.setAccepted(true);
    Request request = deal.getRequest();
    int newVolunteerNumber = request.getVolunteerNum() - 1;
    if(newVolunteerNumber == 0) {
      request.setActive(false);
      dealRepository.deleteByAcceptedFalseAndRequest_Id(request.getId());
    }
    request.setVolunteerNum(newVolunteerNumber);

    requestRepository.save(request);
    dealRepository.save(deal);
  }

  @Override
  public void deleteDeal(Long dealId) {
    dealRepository.findById(dealId).orElseThrow(() -> {
      throw new EntityNotFoundException("Deal with id " + dealId + " does not exist.");
    });

    dealRepository.deleteById(dealId);
  }

  @Override
  public List<DealRequestDTO> getRequestDeals(Long userId) {
    userRepository.findById(userId).orElseThrow(() ->
            new EntityNotFoundException("User with id " + userId + " does not exist.")
    );
    List<Deal> deals = dealRepository.findDealsByAdvert_User_IdAndSenderAndAcceptedTrueOrSenderAndAdvert_User_Id(userId, Sender.REQUEST, Sender.ADVERT, userId);

    return deals.stream().map(deal -> modelMapper.map(deal, DealRequestDTO.class)).toList();
  }

  @Override
  public List<DealAdvertDTO> getAdvertDeals(Long userId) {
    userRepository.findById(userId).orElseThrow(() ->
            new EntityNotFoundException("User with id " + userId + " does not exist.")
    );
    List<Deal> deals = dealRepository.findDealsByRequest_User_IdAndSenderAndAcceptedTrueOrSenderAndRequest_User_Id(userId, Sender.ADVERT, Sender.REQUEST, userId);

    return deals.stream().map(deal -> modelMapper.map(deal, DealAdvertDTO.class)).toList();

  }

  @Override
  public void updateRecension(RecensionDTO recensionDTO) {
    dealRepository.findById(recensionDTO.getId()).orElseThrow(() -> {
      throw new EntityNotFoundException("Deal with id " + recensionDTO.getId() + " does not exist.");
    });

    Deal deal = dealRepository.findById(recensionDTO.getId()).get();
    deal.setRecension(recensionDTO.getRecension());

    dealRepository.save(deal);
  }

  @Override
  public void updateNote(NoteDTO noteDTO) {
    dealRepository.findById(noteDTO.getId()).orElseThrow(() -> {
      throw new EntityNotFoundException("Deal with id " + noteDTO.getId() + " does not exist.");
    });

    Deal deal = dealRepository.findById(noteDTO.getId()).get();
    deal.setNote(noteDTO.getNote());

    dealRepository.save(deal);
  }

  @Override
  public List<RecensionResponseDTO> getRecensions(Long userId) {
    List<Deal> deals = dealRepository.findDealsByAdvert_User_IdAndRecensionNotNull(userId);
    return deals.stream().map(deal -> modelMapper.map(deal, RecensionResponseDTO.class)).toList();
  }

  @Override
  public List<NoteResponseDTO> getNotes(Long userId) {
    List<Deal> deals = dealRepository.findDealsByRequest_User_IdAndNoteNotNull(userId);
    return deals.stream().map(deal -> modelMapper.map(deal, NoteResponseDTO.class)).toList();
  }

  @Override
  public List<DealResponseDTO> getDeals(Long requestId) {

    List<Deal> deals = dealRepository.findDealsByAcceptedFalseAndRequest_IdAndSender(requestId, Sender.ADVERT);

    return deals.stream().map(deal -> modelMapper.map(deal, DealResponseDTO.class)).toList();

  }
}
