package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.user.UserResponseDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;

import java.util.List;

public interface UserService {
  List<UserResponseDTO> getAllUsers();

  void changeBlockStatus(Long userId);

  UserResponseDTO getProfile(Long userId);

  void updateProfile(UserProfileDTO userProfileDTO);
}
