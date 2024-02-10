package hr.fer.unifier.backend.service.impl;


import hr.fer.unifier.backend.api.user.UserResponseDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;
import hr.fer.unifier.backend.db.UserDao;
import hr.fer.unifier.backend.db.entity.User;
import hr.fer.unifier.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserDao userDao;

  private final ModelMapper modelMapper;

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
