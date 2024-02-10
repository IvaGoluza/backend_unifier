package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.user.UserLoginDTO;
import hr.fer.unifier.backend.api.user.UserRegistrationDTO;
import hr.fer.unifier.backend.api.user.UserResponseDTO;


public interface AuthService {
    UserResponseDTO registration(UserRegistrationDTO userRegistrationDto);

    UserResponseDTO login(UserLoginDTO userLoginDTO);

}
