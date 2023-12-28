package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.model.DTO.RequestDTO;
import hr.fer.unifier.backend.model.DTO.RequestResponseDTO;
import hr.fer.unifier.backend.model.Request;
import hr.fer.unifier.backend.model.User;
import hr.fer.unifier.backend.repository.RequestRepository;
import hr.fer.unifier.backend.repository.UserRepository;
import hr.fer.unifier.backend.service.RequestService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

  private final RequestRepository requestRepository;
  private final ModelMapper modelMapper;
  private final UserRepository userRepository;

  @Autowired
  public RequestServiceImpl(RequestRepository requestRepository, ModelMapper modelMapper, UserRepository userRepository) {
    this.requestRepository = requestRepository;
    this.modelMapper = modelMapper;
    this.userRepository = userRepository;
  }

  @Override
  public RequestResponseDTO saveRequest(RequestDTO requestDTO) {
    Request request = modelMapper.map(requestDTO, Request.class);
    User requestUser = userRepository.findById(requestDTO.getUserId()).orElseThrow(() ->
            new EntityNotFoundException("User with id " + requestDTO.getUserId() + " does not exist.")
    );
    request.setUser(requestUser);
    request.setActive(true);
    request.setDeleted(false);
    request = requestRepository.save(request);
    return modelMapper.map(request, RequestResponseDTO.class);
  }

  @Override
  public void deleteRequest(Long id) {
    requestRepository.findById(id).orElseThrow(() -> {
      throw new EntityNotFoundException("Request with id: " + id + " doesn't exists.");
    });
    Request request = requestRepository.findById(id).get();
    request.setDeleted(true);
    requestRepository.save(request);
  }

  @Override
  public List<RequestResponseDTO> getRequests(Long userId) {
    userRepository.findById(userId).orElseThrow(() -> {
      throw new EntityNotFoundException("User with id: " + userId + " not found.");
    });
    List<Request> requests = requestRepository.findByUserIdAndDeletedFalse(userId);
    return requests.stream().map(request -> modelMapper.map(request, RequestResponseDTO.class)).collect(Collectors.toList());
  }

  @Override
  public List<RequestResponseDTO> getAllRequests() {
    List<Request> requests = requestRepository.findAllByActiveTrueAndDeletedFalse();
    return requests.stream().map(request -> modelMapper.map(request, RequestResponseDTO.class)).collect(Collectors.toList());
  }

}
