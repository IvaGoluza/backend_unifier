package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.deal.NoteResponseDTO;
import hr.fer.unifier.backend.api.deal.RecensionResponseDTO;
import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;
import hr.fer.unifier.backend.db.DealDao;
import hr.fer.unifier.backend.db.user.OrganizationDao;
import hr.fer.unifier.backend.db.user.PersonDao;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.Organization;
import hr.fer.unifier.backend.db.user.entity.Person;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ModelMapper modelMapper;

    private final UserDao userDao;

    private final PersonDao personDao;

    private final OrganizationDao organizationDao;

    private final DealDao dealDao;



    @Transactional
    @Override
    public void updateProfile(UserProfileDTO userProfileDTO) {
        final User user = userDao.findById(userProfileDTO.getId()).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userProfileDTO.getId() + " does not exist.")
        );

        user.setProfileDescription(userProfileDTO.getProfileDescription());
        user.setMobilePhone(userProfileDTO.getMobilePhone());
    }

    @Override
    public List<RecensionResponseDTO> getRecensions(Long userId) {
        final User user = userDao.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " does not exist.")
        );

        return dealDao.findByAdvert_UserAndRecensionNotNull(user)
                .orElse(Collections.emptyList())
                .stream()
                .map(deal -> modelMapper.map(deal, RecensionResponseDTO.class))
                .toList();
    }

    @Override
    public List<NoteResponseDTO> getNotes(Long userId) {
        final User user = userDao.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " does not exist.")
        );

        return dealDao.findByRequest_UserAndNoteNotNull(user)
                .orElse(Collections.emptyList())
                .stream()
                .map(deal -> modelMapper.map(deal, NoteResponseDTO.class))
                .toList();
    }
    @Transactional(readOnly = true)
    @Override
    public PersonProfileDTO getPersonProfile(Long userId) {
        final Person person = (Person) personDao.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id %d doesn't exists", userId))
        );

        return modelMapper.map(person, PersonProfileDTO.class);
    }
    @Transactional(readOnly = true)
    @Override
    public OrganizationProfileDTO getOrganizationProfile(Long userId) {
        final Organization organization = (Organization) organizationDao.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Organization with id %d doesn't exists", userId))
        );

        return modelMapper.map(organization, OrganizationProfileDTO.class);
    }
}
