package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.user.UserLoginDTO;
import hr.fer.unifier.backend.api.user.UserRegistrationDTO;
import hr.fer.unifier.backend.api.user.UserResponseDTO;
import hr.fer.unifier.backend.api.user.auth.AuthRequestDTO;
import hr.fer.unifier.backend.api.user.auth.AuthResponseDTO;
import hr.fer.unifier.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthResource {
    private final AuthService authService;

    @PostMapping("/registration")
    public UserResponseDTO registration(@RequestBody UserRegistrationDTO userRegistrationDTO){
        return authService.registration(userRegistrationDTO);
    }

    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody UserLoginDTO userLoginDTO){
        return authService.login(userLoginDTO);
    }

    @PostMapping("/refresh-token")
    public AuthResponseDTO refreshToken(@RequestBody AuthRequestDTO authRequestDTO){
        return authService.refreshToken(authRequestDTO);
    }
}
