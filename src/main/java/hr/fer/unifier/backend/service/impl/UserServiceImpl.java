package hr.fer.unifier.backend.service.impl;


import hr.fer.unifier.backend.api.user.AllUsersDTO;
import hr.fer.unifier.backend.api.user.PasswordResetTokenRequestDTO;
import hr.fer.unifier.backend.api.user.UserCardInfoDTO;
import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final ModelMapper modelMapper;

    private final StreamingUtil streamingUtil;

    private final UserMapper userMapper;

    private final PasswordResetTokenDao passwordResetTokenDao;

    private final EmailService emailService;


    @Transactional(readOnly = true)
    @Override
    public AllUsersDTO getAllUsers() {
        final List<PersonProfileDTO> volunteers = personDao.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getId))
                .map(volunteer -> modelMapper.map(volunteer, PersonProfileDTO.class))
                .toList();

        final List<OrganizationProfileDTO> organizations = organizationDao.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getId))
                .map(organization -> modelMapper.map(organization, OrganizationProfileDTO.class))
                .toList();

        return new AllUsersDTO(volunteers, organizations);
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

    private PasswordResetToken createToken(User user) {
        final PasswordResetToken passwordResetToken = new PasswordResetToken();

        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(PasswordResetToken.EXPIRATION));

        return passwordResetTokenDao.save(passwordResetToken);
    }

}
