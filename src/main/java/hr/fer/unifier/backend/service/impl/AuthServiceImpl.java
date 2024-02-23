package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.user.UserLoginDTO;
import hr.fer.unifier.backend.api.user.auth.AuthRequestDTO;
import hr.fer.unifier.backend.api.user.auth.AuthTokenDTO;
import hr.fer.unifier.backend.api.user.auth.AuthenticationResponseDTO;
import hr.fer.unifier.backend.api.user.register.OrganizationRegisterDTO;
import hr.fer.unifier.backend.api.user.register.PersonRegisterDTO;
import hr.fer.unifier.backend.db.user.OrganizationDao;
import hr.fer.unifier.backend.db.user.PersonDao;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.Organization;
import hr.fer.unifier.backend.db.user.entity.Person;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.Role;
import hr.fer.unifier.backend.enums.UserType;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;

    private final OrganizationDao organizationDao;

    private final PersonDao personDao;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public AuthenticationResponseDTO registerPerson(final PersonRegisterDTO personRegisterDTO, MultipartFile file) {
        userDao.findByEmail(personRegisterDTO.getEmail())
                .ifPresent(user -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Email %s already exists!", personRegisterDTO.getEmail()));
                });

        final Person person = createPerson(personRegisterDTO);
        saveFile(file, person);
        return createAuthenticationResponseDTO(person);
    }


    @Transactional
    @Override
    public AuthenticationResponseDTO registerOrganization(OrganizationRegisterDTO organizationRegisterDTO, MultipartFile file) {
        userDao.findByEmail(organizationRegisterDTO.getEmail())
                .ifPresent(user -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Email %s already exists!", organizationRegisterDTO.getEmail()));
                });

        final Organization organization = createOrganization(organizationRegisterDTO);
        saveFile(file, organization);
        return createAuthenticationResponseDTO(organization);
    }



    @Transactional(readOnly = true)
    @Override
    public AuthenticationResponseDTO login(UserLoginDTO userLoginDTO) {
        final User user = userDao.findByEmail(userLoginDTO.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect.")
        );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getEmail(), userLoginDTO.getPassword()
                )
        );

        return createAuthenticationResponseDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public AuthTokenDTO refreshToken(AuthRequestDTO authRequestDTO) {
        Long userId = jwtService.extractRefreshUserId(authRequestDTO.getRefreshToken());
        final User user = userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d doesn't exists", userId)));

        if(!jwtService.isRefreshTokenValid(authRequestDTO.getRefreshToken(), user)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return new AuthTokenDTO(
                createAuthToken(user),
                null
        );
    }

    private void saveFile(MultipartFile file, User user) {
        if (file == null) return;

        if (file.getContentType() != null && !file.getContentType().endsWith("pdf")){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Datoteka mora biti u pdf obliku!");
        }

        try{
            user.setFile(file.getBytes());
        }catch (IOException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't save file");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private AuthenticationResponseDTO createAuthenticationResponseDTO(User user) {
        return new AuthenticationResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getMobilePhone(),
                user.getRole(),
                user.getUserType(),
                user.isBlocked(),
                createAuthTokens(user)
        );
    }

    private AuthTokenDTO createAuthTokens(final User user){
        String accessToken = createAuthToken(user);
        String refreshToken = createRefreshToken(user);

        return new AuthTokenDTO(
                accessToken,refreshToken
        );
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

    private Person createPerson(final PersonRegisterDTO personRegisterDTO) {
        final Person person = modelMapper.map(personRegisterDTO, Person.class);

        person.setPassword(passwordEncoder.encode(personRegisterDTO.getPassword()));
        person.setRole(Role.USER);
        person.setUserType(UserType.lookup(personRegisterDTO.getUserType()));

        return personDao.save(person);
    }

    private Organization createOrganization(OrganizationRegisterDTO organizationRegisterDTO) {
        final Organization organization = modelMapper.map(organizationRegisterDTO, Organization.class);

        organization.setPassword(passwordEncoder.encode(organizationRegisterDTO.getPassword()));
        organization.setRole(Role.USER);
        organization.setUserType(UserType.VOLUNTEER);

        return organizationDao.save(organization);
    }
}
