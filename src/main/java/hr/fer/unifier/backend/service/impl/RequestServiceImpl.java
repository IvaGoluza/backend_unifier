package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.api.request.RequestsInfoDTO;
import hr.fer.unifier.backend.api.request.RequestsTitlesDTO;
import hr.fer.unifier.backend.config.core.UserLocalThread;
import hr.fer.unifier.backend.db.DealDao;
import hr.fer.unifier.backend.db.RequestDao;
import hr.fer.unifier.backend.db.entity.Deal;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.DealStatusEnum;
import hr.fer.unifier.backend.enums.UserActionType;
import hr.fer.unifier.backend.enums.UserType;
import hr.fer.unifier.backend.mapper.RequestMapper;
import hr.fer.unifier.backend.service.RequestService;
import hr.fer.unifier.backend.service.UserService;
import hr.fer.unifier.backend.util.pagination.PageUtil;
import hr.fer.unifier.backend.util.pagination.UnifierPage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static hr.fer.unifier.backend.util.specification.RequestSpecification.*;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final DealDao dealDao;
    private final RequestDao requestDao;
    private final RequestMapper requestMapper;
    private final UserDao userDao;
    private final UserService userService;

    @Override
    @Transactional
    public RequestResponseDTO saveRequest(final RequestDTO requestDTO) {
        final User requestUser = userDao.findById(requestDTO.getUserId()).orElseThrow(() ->
                new EntityNotFoundException("User with id " + requestDTO.getUserId() + " does not exist.")
        );

        if (requestUser.isBlocked()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Korisnik je trenutno blokiran, ne možete raditi zahtjeve!");
        }

        if (requestUser.getUserType().equals(UserType.VOLUNTEER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Volunteer can't make a request");
        }

        final Request request = requestDao.save(requestMapper.toRequest(requestDTO, requestUser));
        return requestMapper.toRequestResponseDTO(request);
    }

    @Override
    @Transactional
    public void archive(Long id) {
        final Request request = requestDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Request with id: " + id + " doesn't exists.")
        );

        if (!UserLocalThread.getUserId().equals(request.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permission! You don't own this request to change it's status!");
        }

        request.setDeleted(true);
        request.setActive(false);
    }

    @Override
    @Transactional
    public void undoArchive(Long id) {
        final Request request = requestDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Request with id: " + id + " doesn't exists.")
        );

        if (!UserLocalThread.getUserId().equals(request.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permission! You don't own this request to change it's status!");
        }

        request.setDeleted(false);
        request.setActive(true);
    }

    @Override
    @Transactional(readOnly = true)
    public RequestResponseDTO getRequest(Long userId, Long requestId) {
        userService.getUserById(userId);

        final Request request = requestDao.findById(requestId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Request with id: %d not found.", requestId))
        );
        return this.toRequestResponseDTO(request);

    }

    @Override
    @Transactional(readOnly = true)
    public UnifierPage<RequestResponseDTO> getAllRequests(String city, String category, String helpType, Long userId, Pageable pageable) {
        Specification<Request> filters = Specification
                .where(StringUtils.isBlank(city) ? null : inCity(city))
                .and(StringUtils.isBlank(category) ? null : hasCategory(category))
                .and(StringUtils.isBlank(helpType) ? null : hasHelpType(helpType));
        return PageUtil.toPage(
                requestDao.findAll(filters)
                        .stream()
                        .filter(request -> request.getActive() && !request.getDeleted())
                        .map(this::toRequestResponseDTO)
                        .peek(advertResponseDTO -> addDealStatus(userId, advertResponseDTO))
                        .toList(),
                pageable
        );
        //TODO riješiti ovaj problem
//        return PageUtil.map(
//                requestDao.findAllByActiveTrueAndDeletedFalse(filters, pageable),
//                this::toRequestResponseDTO
//        );
    }

    private void addDealStatus(final Long userId, final RequestResponseDTO requestResponseDTO) {
        final Deal deal = dealDao.findDealByRequest(
                requestDao.getReferenceById(requestResponseDTO.getRequestId()),
                userDao.getReferenceById(userId)
        ).orElse(null);

        if (deal != null) {
            final String dealStatus = DealStatusEnum.getDealStatus(deal.getAccepted()).name();
            requestResponseDTO.setDealStatus(dealStatus);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UnifierPage<RequestsInfoDTO> getRequestInfo(Long userId, Pageable pageable) {
        final User user = userService.getUserById(userId);
        Page<Request> allRequests = requestDao.findAllByUserOrderByRequestIdDesc(user, pageable);
        return PageUtil.map(allRequests, requestMapper::toRequestsInfoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public UnifierPage<RequestsTitlesDTO> getRequestTitles(Long userId, Pageable pageable) {
        final User user = userService.getUserById(userId);
        Page<Request> allRequests = requestDao.findAllByUserOrderByRequestIdDesc(user, pageable);
        return PageUtil.map(allRequests, requestMapper::toRequestsTitlesDTO);
    }

    private RequestResponseDTO toRequestResponseDTO(Request request) {
        final RequestResponseDTO requestResponseDTO = requestMapper.toRequestResponseDTO(request);

        requestResponseDTO.setTypeOfAction(
                UserActionType.getActionDescription(request.getOneTime())
        );

        return requestResponseDTO;
    }

}
