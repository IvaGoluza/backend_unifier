package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;
import hr.fer.unifier.backend.db.user.OrganizationDao;
import hr.fer.unifier.backend.db.user.PersonDao;
import hr.fer.unifier.backend.db.user.entity.Organization;
import hr.fer.unifier.backend.db.user.entity.Person;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.service.ProfileService;
import hr.fer.unifier.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static hr.fer.unifier.backend.util.FileUtil.validateHealthCertificate;
import static hr.fer.unifier.backend.util.FileUtil.validateImage;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ModelMapper modelMapper;

    private final PersonDao personDao;

    private final OrganizationDao organizationDao;

    private final UserService userService;


    @Transactional
    @Override
    public void updateProfileImage(Long userId, MultipartFile file) {
        final User user = userService.getUserById(userId);
        validateImage(file);
        try {
            user.setImage(file.getBytes());
        } catch (IOException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Coulnd't upload image", ex);
        }
    }

    @Override
    public void uploadHealthCertificate(Long userId, MultipartFile file) {
        final Person person = personDao.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Volunteer with id = %d doesn't exists!", userId))
        );
        validateHealthCertificate(file);
        try {
            person.setHealthCertificate(file.getBytes());
        } catch (IOException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Coulnd't upload healthcare certificate!", ex);
        }
    }

    @Transactional
    @Override
    public void updateProfile(UserProfileDTO userProfileDTO) {
        final User user = userService.getUserById(userProfileDTO.getUserId());

        user.setProfileDescription(userProfileDTO.getProfileDescription());
        user.setMobilePhone(userProfileDTO.getMobilePhone());
    }


    @Transactional(readOnly = true)
    @Override
    public PersonProfileDTO getPersonProfile(Long userId) {
        final Person person = personDao.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id %d doesn't exists", userId))
        );

        return modelMapper.map(person, PersonProfileDTO.class);
    }
    @Transactional(readOnly = true)
    @Override
    public OrganizationProfileDTO getOrganizationProfile(Long userId) {
        final Organization organization = organizationDao.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Organization with id %d doesn't exists", userId))
        );

        return modelMapper.map(organization, OrganizationProfileDTO.class);
    }
}
