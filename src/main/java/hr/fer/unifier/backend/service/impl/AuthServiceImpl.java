package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.location.AddressResponseDTO;
import hr.fer.unifier.backend.api.user.auth.AuthRequestDTO;
import hr.fer.unifier.backend.api.user.auth.AuthResponseDTO;
import hr.fer.unifier.backend.api.user.login.UserLoginDTO;
import hr.fer.unifier.backend.api.user.login.UserLoginResponseDTO;
import hr.fer.unifier.backend.api.user.register.*;
import hr.fer.unifier.backend.db.entity.Address;
import hr.fer.unifier.backend.db.user.OrganizationDao;
import hr.fer.unifier.backend.db.user.PersonDao;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.Organization;
import hr.fer.unifier.backend.db.user.entity.Person;
import hr.fer.unifier.backend.db.user.entity.User;
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

    private final OrganizationDao organizationDao;

    private final PersonDao personDao;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public PersonResponseDTO registerPerson(final PersonRegisterDTO personRegisterDTO) {
        userDao.findByEmail(personRegisterDTO.getBaseUserDetails().getEmail())
                .ifPresent(user -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Email %s already exists!", personRegisterDTO.getBaseUserDetails().getEmail()));
                });

        final Person person = createPerson(personRegisterDTO);
        return createPersonResponse(person);
    }


    @Transactional
    @Override
    public OrganizationResponseDTO registerOrganization(OrganizationRegisterDTO organizationRegisterDTO) {
        userDao.findByEmail(organizationRegisterDTO.getBaseUserDetails().getEmail())
                .ifPresent(user -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Email %s already exists!", organizationRegisterDTO.getBaseUserDetails().getEmail()));
                });

        final Organization organization = createOrganization(organizationRegisterDTO);
        return createOrganizationResponse(organization);
    }



    @Transactional(readOnly = true)
    @Override
    public UserLoginResponseDTO login(UserLoginDTO userLoginDTO) {
        final User user = userDao.findByEmail(userLoginDTO.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect.")
        );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getEmail(), userLoginDTO.getPassword()
                )
        );

        return new UserLoginResponseDTO(
                modelMapper.map(user, UserResponseDTO.class),
                createAuthResponse(user)
        );
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
                null
        );
    }

    private AuthResponseDTO createAuthResponse(final User user){
        String authJwtToken = createAuthToken(user);
        String refreshToken = createRefreshToken(user);

        return new AuthResponseDTO(
                authJwtToken,refreshToken
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
        final Person person = modelMapper.map(personRegisterDTO.getBaseUserDetails(), Person.class);

        person.setPassword(passwordEncoder.encode(personRegisterDTO.getBaseUserDetails().getPassword()));
        person.setFirstName(personRegisterDTO.getFirstName());
        person.setLastName(personRegisterDTO.getLastName());
        person.setRole(Role.USER);

        return personDao.save(person);
    }

    private Organization createOrganization(OrganizationRegisterDTO organizationRegisterDTO) {
        final Organization organization = modelMapper.map(organizationRegisterDTO.getBaseUserDetails(), Organization.class);

        organization.setPassword(passwordEncoder.encode(organizationRegisterDTO.getBaseUserDetails().getPassword()));
        organization.setAddress(modelMapper.map(organizationRegisterDTO.getAddress(), Address.class));
        organization.setName(organizationRegisterDTO.getName());
        organization.setUrl(organizationRegisterDTO.getUrl());
        organization.setOib(organizationRegisterDTO.getOib());
        organization.setType(organizationRegisterDTO.getType());
        organization.setRole(Role.USER);

        return organizationDao.save(organization);
    }

    private OrganizationResponseDTO createOrganizationResponse(Organization organization) {
        return new OrganizationResponseDTO(
                modelMapper.map(organization, UserResponseDTO.class),
                organization.getName(),
                organization.getType(),
                modelMapper.map(organization.getAddress(), AddressResponseDTO.class),
                organization.getUrl(),
                createAuthResponse(organization)
        );
    }

    private PersonResponseDTO createPersonResponse(Person person) {
        return new PersonResponseDTO(
                modelMapper.map(person, UserResponseDTO.class),
                person.getFirstName(),
                person.getLastName(),
                createAuthResponse(person)
        );
    }
}
