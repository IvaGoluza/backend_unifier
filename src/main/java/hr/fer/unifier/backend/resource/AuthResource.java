package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.user.UserLoginDTO;
import hr.fer.unifier.backend.api.user.auth.AuthRequestDTO;
import hr.fer.unifier.backend.api.user.auth.AuthTokenDTO;
import hr.fer.unifier.backend.api.user.auth.AuthenticationResponseDTO;
import hr.fer.unifier.backend.api.user.register.OrganizationRegisterDTO;
import hr.fer.unifier.backend.api.user.register.PersonRegisterDTO;
import hr.fer.unifier.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class AuthResource {
    private final AuthService authService;

    @PostMapping(path = "/person-registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AuthenticationResponseDTO registration(@RequestPart PersonRegisterDTO personRegisterDTO, @RequestPart(required = false) MultipartFile file){
        return authService.registerPerson(personRegisterDTO, file);
    }

    @PostMapping(path = "/organization-registration")
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
