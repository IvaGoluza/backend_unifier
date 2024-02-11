package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.user.auth.AuthRequestDTO;
import hr.fer.unifier.backend.api.user.auth.AuthResponseDTO;
import hr.fer.unifier.backend.api.user.login.UserLoginDTO;
import hr.fer.unifier.backend.api.user.login.UserLoginResponseDTO;
import hr.fer.unifier.backend.api.user.register.OrganizationRegisterDTO;
import hr.fer.unifier.backend.api.user.register.OrganizationResponseDTO;
import hr.fer.unifier.backend.api.user.register.PersonRegisterDTO;
import hr.fer.unifier.backend.api.user.register.PersonResponseDTO;
import hr.fer.unifier.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthResource {
    private final AuthService authService;

    @PostMapping("/person-registration")
    public PersonResponseDTO registration(@RequestBody PersonRegisterDTO personRegisterDTO){
        return authService.registerPerson(personRegisterDTO);
    }

    @PostMapping("/organization-registration")
    public OrganizationResponseDTO registration(@RequestBody OrganizationRegisterDTO organizationRegisterDTO){
        return authService.registerOrganization(organizationRegisterDTO);
    }

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO userLoginDTO){
        return authService.login(userLoginDTO);
    }

    @PostMapping("/refresh-token")
    public AuthResponseDTO refreshToken(@RequestBody AuthRequestDTO authRequestDTO){
        return authService.refreshToken(authRequestDTO);
    }
}
