package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.user.UserLoginDTO;
import hr.fer.unifier.backend.api.user.UserRegistrationDTO;
import hr.fer.unifier.backend.api.user.UserResponseDTO;
import hr.fer.unifier.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
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
}
