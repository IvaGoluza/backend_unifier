package hr.fer.unifier.backend.service.impl;


import hr.fer.unifier.backend.api.user.UserLoginDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;
import hr.fer.unifier.backend.api.user.UserRegistrationDTO;
import hr.fer.unifier.backend.api.user.UserResponseDTO;
import hr.fer.unifier.backend.db.entity.User;
import hr.fer.unifier.backend.enums.Role;
import hr.fer.unifier.backend.db.UserDao;
import hr.fer.unifier.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserDao userDao;

  private final ModelMapper modelMapper;


  @Transactional
  @Override
  public UserResponseDTO saveUser(final UserRegistrationDTO userRegistrationDto) {
    User user = modelMapper.map(userRegistrationDto, User.class);

    user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    user.setBlocked(false);
    user.setRole(Role.USER);
    user = userDao.save(user);

    return modelMapper.map(user, UserResponseDTO.class);
  }

  @Transactional(readOnly = true)
  @Override
  public UserResponseDTO checkUser(UserLoginDTO userLoginDTO) {
    User user = userDao.getByEmail(userLoginDTO.getEmail()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect.")
    );
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    if (encoder.matches(userLoginDTO.getPassword(), user.getPassword()) || userLoginDTO.getPassword().equals(user.getPassword())) {
      return modelMapper.map(user, UserResponseDTO.class);
    }

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect.");
  }

  @Transactional(readOnly = true)
  @Override
  public List<UserResponseDTO> getAllUsers() {
    return userDao.findAll()
            .stream()
            .sorted(Comparator.comparing(User::getId)).map(user -> modelMapper.map(user, UserResponseDTO.class))
            .toList();
  }

  @Transactional
  @Override
  public void changeBlockStatus(Long userId) {
    final User user = userDao.findById(userId).orElseThrow(
        () -> new EntityNotFoundException("User with id " + userId + " does not exist.")
    );

    user.setBlocked(!user.isBlocked());
  }

  @Transactional(readOnly = true)
  @Override
  public UserResponseDTO getProfile(Long userId) {
    final User user = userDao.findById(userId).orElseThrow(
            () -> new EntityNotFoundException("User with id " + userId + " does not exist.")
    );

    return modelMapper.map(user, UserResponseDTO.class);
  }

  @Transactional
  @Override
  public void updateProfile(UserProfileDTO userProfileDTO) {
    final User user = userDao.findById(userProfileDTO.getId()).orElseThrow(
            () -> new EntityNotFoundException("User with id " + userProfileDTO.getId() + " does not exist.")
    );

    user.setProfileDescription(userProfileDTO.getProfileDescription());
    user.setMobilePhone(userProfileDTO.getMobilePhone());
  }

}
