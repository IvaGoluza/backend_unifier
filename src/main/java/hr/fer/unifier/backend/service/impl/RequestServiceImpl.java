package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.api.request.RequestsInfoDTO;
import hr.fer.unifier.backend.db.RequestDao;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.UserActionType;
import hr.fer.unifier.backend.enums.UserType;
import hr.fer.unifier.backend.mapper.RequestMapper;
import hr.fer.unifier.backend.service.RequestService;
import hr.fer.unifier.backend.service.UserService;
import hr.fer.unifier.backend.util.pagination.PageUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestDao requestDao;
    private final RequestMapper requestMapper;
    private final UserDao userDao;
    private final UserService userService;

    @Transactional
    @Override
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

    @Transactional
    @Override
    public void archive(Long id) {
        final Request request = requestDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Request with id: " + id + " doesn't exists.")
        );

        request.setDeleted(true);
        request.setActive(false);
    }

    @Transactional
    @Override
    public void undoArchive(Long id) {
        final Request request = requestDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Request with id: " + id + " doesn't exists.")
        );

        request.setDeleted(false);
        request.setActive(true);
    }

    @Transactional(readOnly = true)
    @Override
    public RequestResponseDTO getRequest(Long userId, Long requestId) {
        userService.getUserById(userId);

        final Request request = requestDao.findById(requestId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Request with id: %d not found.", requestId))
        );
        return this.toRequestResponseDTO(request);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<RequestResponseDTO> getAllRequests(Pageable pageable) {
        return PageUtil.map(
                requestDao.findAllByActiveTrueAndDeletedFalse(pageable),
                this::toRequestResponseDTO
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Page<RequestsInfoDTO> getRequestInfo(Long userId, Pageable pageable) {
        final User user = userService.getUserById(userId);
        Page<Request> allRequests = requestDao.findAllByUserOrderByRequestIdDesc(user, pageable);
        return PageUtil.map(allRequests, requestMapper::toRequestsInfoDTO);
    }

    private RequestResponseDTO toRequestResponseDTO(Request request) {
        final RequestResponseDTO requestResponseDTO = requestMapper.toRequestResponseDTO(request);

        requestResponseDTO.setTypeOfAction(
                UserActionType.getActionDescription(request.getOneTime())
        );

        return requestResponseDTO;
    }

}
