package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.model.DTO.UserLoginDTO;
import hr.fer.unifier.backend.model.DTO.UserProfileDTO;
import hr.fer.unifier.backend.model.DTO.UserRegistrationDTO;
import hr.fer.unifier.backend.model.DTO.UserResponseDTO;

import java.util.List;

public interface UserService {

  UserResponseDTO saveUser(UserRegistrationDTO userRegistrationDto);

  UserResponseDTO checkUser(UserLoginDTO userLoginDTO);

  List<UserResponseDTO> getAllUsers();

  void changeBlockStatus(Long userId);

  UserResponseDTO getProfile(Long userId);

  void updateProfile(UserProfileDTO userProfileDTO);
}
