package hr.fer.unifier.backend.service.impl;


import hr.fer.unifier.backend.api.user.*;
import hr.fer.unifier.backend.db.user.OrganizationDao;
import hr.fer.unifier.backend.db.user.PasswordResetTokenDao;
import hr.fer.unifier.backend.db.user.PersonDao;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.PasswordResetToken;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.db.user.entity.UserWithFile;
import hr.fer.unifier.backend.mapper.UserMapper;
import hr.fer.unifier.backend.service.EmailService;
import hr.fer.unifier.backend.service.UserService;
import hr.fer.unifier.backend.util.file.StreamingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final PersonDao personDao;

    private final OrganizationDao organizationDao;

    private final StreamingUtil streamingUtil;

    private final UserMapper userMapper;

    private final PasswordResetTokenDao passwordResetTokenDao;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public AllUsersDTO getAllUsers() {
        final List<PersonDTO> persons = personDao.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getId))
                .map(userMapper::toPersonDTO)
                .toList();

        final List<OrganizationDTO> organizations = organizationDao.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getId))
                .map(userMapper::toOrganizationDTO)
                .toList();

        return new AllUsersDTO(persons, organizations);
    }

    @Transactional
    @Override
    public void changeBlockStatus(Long userId) {
        final User user = getUserById(userId);
        user.setBlocked(!user.isBlocked());
    }

    @Transactional
    @Override
    public void approveUser(Long userId) {
        getUserById(userId).setApproved(true);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<StreamingResponseBody> getUserCertificateOfGoodConduct(Long userId) {
        final UserWithFile user = userDao.getUserWithFile(userId);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't find user with id %d", userId));
        if (user.getFile() == null) return null;

        try {
            Blob blob = new SerialBlob(user.getFile());
            return streamingUtil.getBlobStreamingResponse(String.format("%s.pdf", user.getName()), blob);
        } catch (SQLException sqlException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't read file.", sqlException);
        }
    }

    @Transactional
    @Override
    public UserCardInfoDTO getUserCardInfo(Long userId) {
        getUserById(userId);
        return userMapper.toUserCardInfo(userDao.getUserCardInfo(userId));
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(Long userId) {
        return userDao.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't find user with id %d", userId))
        );
    }

    @Transactional
    @Override
    public void passwordReset(PasswordResetTokenRequestDTO passwordResetTokenRequestDTO) {
        final User user = userDao.findByEmail(passwordResetTokenRequestDTO.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ne postoji korisnik s navedenim email-om")
        );

        final PasswordResetToken passwordResetToken = createToken(user);
        emailService.sendRecoveryMail(user.getEmail(), passwordResetToken.getToken());
    }

    @Transactional
    @Override
    public void updatePassword(ResetPasswordRequestDTO resetPasswordRequestDTO) {
        final PasswordResetToken passwordResetToken = passwordResetTokenDao.findByToken(resetPasswordRequestDTO.getToken()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token couldn't be found")
        );

        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expired");
        }

        passwordResetToken.getUser().setPassword(
                passwordEncoder.encode(resetPasswordRequestDTO.getPassword())
        );
    }

    @Override
    public List<UserSearchResultsDTO> searchVolunteers(Long userId, String name) {
        return userDao.getUserVolunteerSearchResult(userId, name != null ? name : "")
                .stream()
                .map(userMapper::toUserSearchResultsDTO)
                .toList();
    }

    private PasswordResetToken createToken(User user) {
        final PasswordResetToken passwordResetToken = new PasswordResetToken();

        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(PasswordResetToken.EXPIRATION));

        return passwordResetTokenDao.save(passwordResetToken);
    }

}
