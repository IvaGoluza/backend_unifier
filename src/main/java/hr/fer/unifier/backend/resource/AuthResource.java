package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.user.auth.AuthRequestDTO;
import hr.fer.unifier.backend.api.user.auth.AuthTokenDTO;
import hr.fer.unifier.backend.api.user.auth.AuthenticationResponseDTO;
import hr.fer.unifier.backend.api.user.UserLoginDTO;
import hr.fer.unifier.backend.api.user.register.OrganizationRegisterDTO;
import hr.fer.unifier.backend.api.user.register.PersonRegisterDTO;
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
    public AuthenticationResponseDTO registration(@RequestBody PersonRegisterDTO personRegisterDTO){
        return authService.registerPerson(personRegisterDTO);
    }

    @PostMapping("/organization-registration")
    public AuthenticationResponseDTO registration(@RequestBody OrganizationRegisterDTO organizationRegisterDTO){
        return authService.registerOrganization(organizationRegisterDTO);
    }

    @PostMapping("/login")
    public AuthenticationResponseDTO login(@RequestBody UserLoginDTO userLoginDTO){
        return authService.login(userLoginDTO);
    }

    @PostMapping("/refresh-token")
    public AuthTokenDTO refreshToken(@RequestBody AuthRequestDTO authRequestDTO){
        return authService.refreshToken(authRequestDTO);
    }
}
