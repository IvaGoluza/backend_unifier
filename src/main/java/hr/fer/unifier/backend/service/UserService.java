package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.user.register.UserResponseDTO;

import java.util.List;

public interface UserService {
  List<UserResponseDTO> getAllUsers();

  void changeBlockStatus(Long userId);
}
