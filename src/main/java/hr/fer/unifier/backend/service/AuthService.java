package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.user.auth.AuthRequestDTO;
import hr.fer.unifier.backend.api.user.auth.AuthTokenDTO;
import hr.fer.unifier.backend.api.user.auth.AuthenticationResponseDTO;
import hr.fer.unifier.backend.api.user.UserLoginDTO;
import hr.fer.unifier.backend.api.user.register.OrganizationRegisterDTO;
import hr.fer.unifier.backend.api.user.register.PersonRegisterDTO;


public interface AuthService {
    AuthenticationResponseDTO registerPerson(PersonRegisterDTO personRegisterDTO);
    AuthenticationResponseDTO registerOrganization(OrganizationRegisterDTO organizationRegisterDTO);
    AuthenticationResponseDTO login(UserLoginDTO userLoginDTO);
    AuthTokenDTO refreshToken(AuthRequestDTO authRequestDTO);
}
