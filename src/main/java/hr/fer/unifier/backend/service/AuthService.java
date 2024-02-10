package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.user.UserLoginDTO;
import hr.fer.unifier.backend.api.user.UserRegistrationDTO;
import hr.fer.unifier.backend.api.user.UserResponseDTO;
import hr.fer.unifier.backend.api.user.auth.AuthRequestDTO;
import hr.fer.unifier.backend.api.user.auth.AuthResponseDTO;


public interface AuthService {
    UserResponseDTO registration(UserRegistrationDTO userRegistrationDto);

    UserResponseDTO login(UserLoginDTO userLoginDTO);

    AuthResponseDTO refreshToken(AuthRequestDTO authRequestDTO);
}
