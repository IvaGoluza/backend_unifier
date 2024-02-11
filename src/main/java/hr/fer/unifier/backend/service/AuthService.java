package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.user.auth.AuthRequestDTO;
import hr.fer.unifier.backend.api.user.auth.AuthResponseDTO;
import hr.fer.unifier.backend.api.user.login.UserLoginDTO;
import hr.fer.unifier.backend.api.user.login.UserLoginResponseDTO;
import hr.fer.unifier.backend.api.user.register.OrganizationRegisterDTO;
import hr.fer.unifier.backend.api.user.register.OrganizationResponseDTO;
import hr.fer.unifier.backend.api.user.register.PersonRegisterDTO;
import hr.fer.unifier.backend.api.user.register.PersonResponseDTO;


public interface AuthService {
    PersonResponseDTO registerPerson(PersonRegisterDTO personRegisterDTO);
    OrganizationResponseDTO registerOrganization(OrganizationRegisterDTO organizationRegisterDTO);
    UserLoginResponseDTO login(UserLoginDTO userLoginDTO);
    AuthResponseDTO refreshToken(AuthRequestDTO authRequestDTO);
}
