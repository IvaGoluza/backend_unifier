package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.deal.NoteResponseDTO;
import hr.fer.unifier.backend.api.deal.RecensionResponseDTO;
import hr.fer.unifier.backend.api.user.UserResponseDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;
import hr.fer.unifier.backend.db.DealDao;
import hr.fer.unifier.backend.db.UserDao;
import hr.fer.unifier.backend.db.entity.User;
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

    private final DealDao dealDao;


    @Transactional(readOnly = true)
    @Override
    public UserResponseDTO getProfile(Long userId) {
        final User user = userDao.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " does not exist.")
        );

        return modelMapper.map(user, UserResponseDTO.class);
    }

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
}
