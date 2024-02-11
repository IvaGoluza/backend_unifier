package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.entity.User;
import hr.fer.unifier.backend.db.RequestDao;
import hr.fer.unifier.backend.db.UserDao;
import hr.fer.unifier.backend.service.RequestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

  private final RequestDao requestDao;
  private final ModelMapper modelMapper;
  private final UserDao userDao;

  @Transactional
  @Override
  public RequestResponseDTO saveRequest(final RequestDTO requestDTO) {
    Request request = modelMapper.map(requestDTO, Request.class);
    final User requestUser = userDao.findById(requestDTO.getUserId()).orElseThrow(() ->
            new EntityNotFoundException("User with id " + requestDTO.getUserId() + " does not exist.")
    );

    request.setUser(requestUser);
    request.setActive(true);
    request.setDeleted(false);
    request = requestDao.save(request);

    return modelMapper.map(request, RequestResponseDTO.class);
  }

  @Transactional
  @Override
  public void changeDeleteStatus(Long id) {
    final Request request = requestDao.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Request with id: " + id + " doesn't exists.")
    );

    request.setDeleted(true);
  }

  @Override
  public List<RequestResponseDTO> getRequests(Long userId) {
    final User user = userDao.findById(userId).orElseThrow(
            () -> new EntityNotFoundException("User with id: " + userId + " not found.")
    );

    return requestDao.findByUserAndDeletedFalse(user)
            .orElse(Collections.emptyList())
            .stream()
            .map(request -> modelMapper.map(request, RequestResponseDTO.class))
            .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  @Override
  public List<RequestResponseDTO> getAllRequests() {
    return requestDao.findAllByActiveTrueAndDeletedFalse()
            .orElse(Collections.emptyList())
            .stream()
            .map(request -> modelMapper.map(request, RequestResponseDTO.class))
            .toList();
  }

}
