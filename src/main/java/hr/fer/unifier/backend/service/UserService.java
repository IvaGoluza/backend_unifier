package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.user.UserLoginDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;
import hr.fer.unifier.backend.api.user.UserRegistrationDTO;
import hr.fer.unifier.backend.api.user.UserResponseDTO;

import java.util.List;

public interface UserService {

  UserResponseDTO saveUser(UserRegistrationDTO userRegistrationDto);

  UserResponseDTO checkUser(UserLoginDTO userLoginDTO);

  List<UserResponseDTO> getAllUsers();

  void changeBlockStatus(Long userId);

  UserResponseDTO getProfile(Long userId);

  void updateProfile(UserProfileDTO userProfileDTO);
}
