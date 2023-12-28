package hr.fer.unifier.backend.service.impl;


import hr.fer.unifier.backend.model.DTO.UserLoginDTO;
import hr.fer.unifier.backend.model.DTO.UserProfileDTO;
import hr.fer.unifier.backend.model.DTO.UserRegistrationDTO;
import hr.fer.unifier.backend.model.DTO.UserResponseDTO;
import hr.fer.unifier.backend.model.User;
import hr.fer.unifier.backend.model.enums.Role;
import hr.fer.unifier.backend.repository.UserRepository;
import hr.fer.unifier.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final ModelMapper modelMapper;

  @Autowired
  public UserServiceImpl(UserRepository repository, ModelMapper modelMapper) {
    this.userRepository = repository;
    this.modelMapper = modelMapper;
  }

  @Override
  public UserResponseDTO saveUser(UserRegistrationDTO userRegistrationDto) {
    User user = modelMapper.map(userRegistrationDto, User.class);
    user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    user.setBlocked(false);
    user.setRole(Role.USER);
    user = userRepository.save(user);

    return modelMapper.map(user, UserResponseDTO.class);
  }

  @Override
  public UserResponseDTO checkUser(UserLoginDTO userLoginDTO) {
    User user = userRepository.getByEmail(userLoginDTO.getEmail());
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    if (user != null && (encoder.matches(userLoginDTO.getPassword(), user.getPassword()) || userLoginDTO.getPassword().equals(user.getPassword()))) {
      return modelMapper.map(user, UserResponseDTO.class);
    }

    throw new IllegalArgumentException("Email or password is incorrect.");
  }

  @Override
  public List<UserResponseDTO> getAllUsers() {
    return userRepository.findAll().stream().sorted(Comparator.comparing(User::getId)).map(user -> modelMapper.map(user, UserResponseDTO.class)).toList();
  }

  @Override
  public void changeBlockStatus(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new EntityNotFoundException("User with id " + userId + " does not exist."));
    user.setBlocked(!user.isBlocked());
    userRepository.save(user);
  }

  @Override
  public UserResponseDTO getProfile(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(
            () -> new EntityNotFoundException("User with id " + userId + " does not exist."));
    return modelMapper.map(user, UserResponseDTO.class);
  }

  @Override
  public void updateProfile(UserProfileDTO userProfileDTO) {
    User user = userRepository.findById(userProfileDTO.getId()).orElseThrow(
            () -> new EntityNotFoundException("User with id " + userProfileDTO.getId() + " does not exist."));

    user.setProfileDescription(userProfileDTO.getProfileDescription());
    user.setMobilePhone(userProfileDTO.getMobilePhone());
    user = userRepository.save(user);
  }

}
