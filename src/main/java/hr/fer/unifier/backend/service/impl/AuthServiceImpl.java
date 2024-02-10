package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.user.UserLoginDTO;
import hr.fer.unifier.backend.api.user.UserRegistrationDTO;
import hr.fer.unifier.backend.api.user.UserResponseDTO;
import hr.fer.unifier.backend.api.user.auth.AuthRequestDTO;
import hr.fer.unifier.backend.api.user.auth.AuthResponseDTO;
import hr.fer.unifier.backend.db.UserDao;
import hr.fer.unifier.backend.db.entity.User;
import hr.fer.unifier.backend.enums.Role;
import hr.fer.unifier.backend.service.AuthService;
import hr.fer.unifier.backend.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public UserResponseDTO registration(final UserRegistrationDTO userRegistrationDto) {
       userDao.findByEmail(userRegistrationDto.getEmail())
                .ifPresent(user -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Email %s already exists!", userRegistrationDto.getEmail()));
                });

        User user = modelMapper.map(userRegistrationDto, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setBlocked(false);
        user.setRole(Role.USER);
        user = userDao.save(user);

        return createUserResponseDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDTO login(UserLoginDTO userLoginDTO) {
        final User user = userDao.findByEmail(userLoginDTO.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect.")
        );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getEmail(), userLoginDTO.getPassword()
                )
        );

        return createUserResponseDTO(user);
    }

    @Override
    public AuthResponseDTO refreshToken(AuthRequestDTO authRequestDTO) {
        Long userId = jwtService.extractRefreshUserId(authRequestDTO.getRefreshToken());
        final User user = userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d doesn't exists", userId)));

        if(!jwtService.isRefreshTokenValid(authRequestDTO.getRefreshToken(), user)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return new AuthResponseDTO(
                createAuthToken(user),
                createRefreshToken(user)
        );
    }

    private UserResponseDTO createUserResponseDTO(final User user) {
        String authJwtToken = createAuthToken(user);
        String refreshToken = createRefreshToken(user);

        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        userResponseDTO.setAuthToken(authJwtToken);
        userResponseDTO.setRefreshToken(refreshToken);

        return userResponseDTO;
    }

    private String createAuthToken(final User user){
        final HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());

        return jwtService.generateAuthToken(claims, user);
    }

    private String createRefreshToken(final User user){
        final HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());

        return jwtService.generateRefreshToken(claims, user);
    }
}
