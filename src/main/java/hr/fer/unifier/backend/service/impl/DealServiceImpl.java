package hr.fer.unifier.backend.service.impl;


import hr.fer.unifier.backend.api.deal.*;
import hr.fer.unifier.backend.db.AdvertDao;
import hr.fer.unifier.backend.db.DealDao;
import hr.fer.unifier.backend.db.RecensionDao;
import hr.fer.unifier.backend.db.RequestDao;
import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.entity.Deal;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.Sender;
import hr.fer.unifier.backend.mapper.AdvertMapper;
import hr.fer.unifier.backend.mapper.DealMapper;
import hr.fer.unifier.backend.mapper.RequestMapper;
import hr.fer.unifier.backend.service.DealService;
import hr.fer.unifier.backend.service.UserService;
import hr.fer.unifier.backend.util.pagination.PageUtil;
import hr.fer.unifier.backend.util.pagination.UnifierPage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    private final AdvertMapper advertMapper;
    private final RequestMapper requestMapper;
    private final RequestDao requestDao;
    private final AdvertDao advertDao;
    private final RecensionDao recensionDao;
    private final UserDao userDao;

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
        final User receiver = userService.getUserById(dealDTO.getReceiverId());
        final Deal deal = dealDao.save(dealMapper.toDeal(dealDTO));

        deal.setRequest(request);
        deal.setAdvert(advert);
        deal.setSenderId(sender);
        deal.setReceiver(receiver);

        return dealMapper.toDealResponseDTO(deal);
    }


    @Transactional
    @Override
    public void updateAccepted(Long dealId) {
        dealDao.findById(dealId).orElseThrow(() ->
                new EntityNotFoundException("Deal with id " + dealId + " does not exist.")
        );

        final Deal deal = dealDao.findById(dealId).orElseThrow();

        if (deal.getAccepted() != null && deal.getAccepted())  {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Deal is already accepted");
        }

        deal.setAccepted(true);

        final Request request = deal.getRequest();
        int newVolunteerNumber = request.getNumOfVolunteers() - 1;
        if (newVolunteerNumber == 0) {
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
    public UnifierPage<VolunteerHelpApplicationDTO> getVolunteersHelpApplications(Long requestId, Pageable pageable) {
        final Request request = requestDao.findById(requestId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Ne postoji zahtjev s id = %d", requestId))
        );
        if (!request.getActive()) {
            return null;
        }

        return PageUtil.map(
                dealDao.findAllByRequestAndSender(request, Sender.VOLUNTEER, pageable),
                this::createVolunteerHelpApplications
        );

    }

    @Transactional
    @Override
    public UnifierPage<PersonInNeedApplicationDTO> getPersonInNeedApplications(Long advertId, Pageable pageable) {
        final Advert advert = advertDao.findById(advertId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Ne postoji volonterski oglas s id = %d", advertId))
        );

        return PageUtil.map(dealDao.findAllByAdvertAndSender(advert, Sender.PERSON_IN_NEED, pageable),this::createPersonInNeedApplicationDTO);
    }

    @Transactional
    @Override
    public UnifierPage<AcceptedPersonInNeedDealsDTO> getAcceptedDealsForPersonInNeed(Long userId, Pageable pageable) {
        final User user = userService.getUserById(userId);
        final List<AcceptedPersonInNeedDealsDTO> acceptedDeals = dealDao.findAllBySenderIdOrRequest_UserOrderByDealIdDesc(user,user)
                .orElse(Collections.emptyList())
                .stream()
                .filter(deal -> filterDealForPersonInNeed(deal, userId))
                .map(deal ->toAcceptedPersonInNeedDealsDTO(deal, userId))
                .toList();

        return PageUtil.toPage(acceptedDeals,pageable);
    }

    @Transactional
    @Override
    public UnifierPage<AcceptedDealsVolunteerDTO> getAcceptedDealsForVolunteer(Long userId, Pageable pageable) {
        final User user = userService.getUserById(userId);
        final List<AcceptedDealsVolunteerDTO> acceptedDeals = dealDao.findAllBySenderIdOrAdvert_UserOrderByDealIdDesc(user,user)
                .orElse(Collections.emptyList())
                .stream()
                .filter(deal -> filterDealForVolunteer(deal, userId))
                .map(deal ->toAcceptedDealsVolunteerDTO(deal, userId))
                .toList();

        return PageUtil.toPage(acceptedDeals,pageable);
    }

    @Transactional
    @Override
    public void updateVolunteerDealDescription(Long dealId, VolunteerDealDescriptionDTO volunteerDealDescriptionDTO) {
        final Deal deal = dealDao.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal with id " + dealId + " does not exist.")
        );

        final String workDescription = volunteerDealDescriptionDTO.getVolunteerWorkDescription();
        if (workDescription == null || workDescription.isBlank()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Nedostaje opis volonterskog posla!");
        }

        final String volunteerPosition = volunteerDealDescriptionDTO.getVolunteerPosition();
        if (volunteerPosition == null || volunteerPosition.isBlank()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Nedostaje volonterska pozicija!");
        }

        deal.setVolunteerPosition(volunteerDealDescriptionDTO.getVolunteerPosition());
        deal.setVolunteerWorkDescription(volunteerDealDescriptionDTO.getVolunteerWorkDescription());
    }

    @Transactional
    @Override
    public void rejectDeal(Long dealId) {
        final Deal deal = dealDao.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal with id " + dealId + " does not exist.")
        );

        deal.setAccepted(false);
    }

    private boolean filterDealForPersonInNeed(Deal deal, Long personInNeedId) {
        boolean isPersonInNeed = false;

        if (deal.getSenderId().getId().equals(personInNeedId)){
            if (deal.getAdvert() != null && !deal.getAdvert().getUser().getId().equals(personInNeedId)){
                isPersonInNeed = true;
            }
        }else {
            isPersonInNeed = deal.getRequest().getUser().getId().equals(personInNeedId);
        }

        return isPersonInNeed && deal.getAccepted() != null && deal.getAccepted();
    }

    private boolean filterDealForVolunteer(Deal deal, Long volunteerId) {
        boolean isVolunter = false;

        if (deal.getSenderId().getId().equals(volunteerId)){
            if (!deal.getRequest().getUser().getId().equals(volunteerId)){
                isVolunter = true;
            }
        }else {
            isVolunter = deal.getAdvert().getUser().getId().equals(volunteerId);
        }

        return isVolunter && deal.getAccepted() != null && deal.getAccepted();
    }

    private AcceptedPersonInNeedDealsDTO toAcceptedPersonInNeedDealsDTO(Deal deal, Long personInNeedId) {
        final AcceptedPersonInNeedDealsDTO acceptedDeal = new AcceptedPersonInNeedDealsDTO();
        acceptedDeal.setDealId(deal.getDealId());
        if (deal.getAdvert() != null){
            acceptedDeal.setVolunteerApplicationAdvert(advertMapper.toAcceptedDealAdvertResponseDTO(deal.getAdvert()));
            acceptedDeal.getVolunteerApplicationAdvert().setHasImage(deal.getAdvert().getAdvertImage() != null);
        }

        if (deal.getRequest() != null){
            acceptedDeal.setPersonInNeedRequest(requestMapper.toAcceptedDealRequestResponseDTO(deal.getRequest()));
        }

        if (deal.getMessage() != null){
            if (deal.getSender().equals(Sender.VOLUNTEER)){
                acceptedDeal.setVolunteerApplicationMessage(deal.getMessage());
            }else {
                acceptedDeal.setPersonInNeedMessage(deal.getMessage());
            }
        }

        acceptedDeal.setRecensionFulfilled(recensionDao.existsByDeal(deal));
        acceptedDeal.setContractDetailsFulfilled(
                deal.getVolunteerPosition() != null && deal.getVolunteerWorkDescription() != null
        );
        final String volunteerName;
        final Long volunteerId;
        if (!deal.getSenderId().getId().equals(personInNeedId)){
            volunteerId = deal.getSenderId().getId();
            volunteerName = userDao.getUserCardInfo(deal.getSenderId().getId()).getName();
        }else {
            volunteerId = deal.getAdvert().getUser().getId();
            volunteerName = userDao.getUserCardInfo(deal.getAdvert().getUser().getId()).getName();
        }

        acceptedDeal.setVolunteerName(volunteerName);
        acceptedDeal.setVolunteerId(volunteerId);

        return acceptedDeal;
    }

    private AcceptedDealsVolunteerDTO toAcceptedDealsVolunteerDTO(Deal deal, Long personInNeedId) {
        final AcceptedDealsVolunteerDTO acceptedDeal = new AcceptedDealsVolunteerDTO();
        acceptedDeal.setDealId(deal.getDealId());

        if (deal.getAdvert() != null){
            acceptedDeal.setVolunteerApplicationAdvert(advertMapper.toAcceptedDealAdvertResponseDTO(deal.getAdvert()));
            acceptedDeal.getVolunteerApplicationAdvert().setHasImage(deal.getAdvert().getAdvertImage() != null);
        }

        if (deal.getRequest() != null){
            acceptedDeal.setPersonInNeedRequest(requestMapper.toAcceptedDealRequestResponseDTO(deal.getRequest()));
        }

        if (deal.getMessage() != null){
            if (deal.getSender().equals(Sender.VOLUNTEER)){
                acceptedDeal.setVolunteerApplicationMessage(deal.getMessage());
            }else {
                acceptedDeal.setPersonInNeedMessage(deal.getMessage());
            }
        }

        acceptedDeal.setReviewed(recensionDao.existsByDeal(deal));
        acceptedDeal.setContractReady(
                deal.getVolunteerPosition() != null && deal.getVolunteerWorkDescription() != null
        );
        final String personInNeedName;

        if (!deal.getSenderId().getId().equals(personInNeedId)){
            personInNeedName = userDao.getUserCardInfo(deal.getSenderId().getId()).getName();
        }else {
            personInNeedName = userDao.getUserCardInfo(deal.getRequest().getUser().getId()).getName();
        }

        acceptedDeal.setPersonInNeedName(personInNeedName);
        return acceptedDeal;
    }
    private void validateDealRequest(DealDTO dealDTO) {
        if (dealDTO.getSender() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nedostaje sender!");
        }

        if (dealDTO.getSender().equals(Sender.PERSON_IN_NEED)) {
            if (dealDTO.getAdvertId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nedostaje advert id!");
            }

            if (dealDTO.getMessage() == null && dealDTO.getRequestId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Potrebno je priložit ili zahtjev ili poruku!");
            }
        } else {
            if (dealDTO.getRequestId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nedostaje request id!");
            }

            if (dealDTO.getMessage() == null && dealDTO.getAdvertId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Potrebno je priložit ili oglas ili poruku!");
            }
        }
    }

    private PersonInNeedApplicationDTO createPersonInNeedApplicationDTO(Deal deal) {
        final PersonInNeedApplicationDTO personInNeedApplicationDTO = dealMapper.toPersonInNeedApplicationDTO(deal);

        personInNeedApplicationDTO.setUser(userService.getUserCardInfo(deal.getSenderId().getId()));
        personInNeedApplicationDTO.getRequest().setUser(null);

        return personInNeedApplicationDTO;
    }

    private VolunteerHelpApplicationDTO createVolunteerHelpApplications(Deal deal) {
        final VolunteerHelpApplicationDTO volunteerHelpApplicationDTO = dealMapper.toVolunteerHelpApplicationDTO(deal);

        volunteerHelpApplicationDTO.setUser(userService.getUserCardInfo(deal.getSenderId().getId()));
        volunteerHelpApplicationDTO.getAdvert().setUser(null);

        return volunteerHelpApplicationDTO;
    }
}
