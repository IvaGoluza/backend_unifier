package hr.fer.unifier.backend.service.impl;


import hr.fer.unifier.backend.api.user.register.UserResponseDTO;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.User;
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
}
