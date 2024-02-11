package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.deal.NoteResponseDTO;
import hr.fer.unifier.backend.api.deal.RecensionResponseDTO;
import hr.fer.unifier.backend.api.user.UserResponseDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;

import java.util.List;

public interface ProfileService {
    void updateProfile(UserProfileDTO userProfileDTO);

    List<RecensionResponseDTO> getRecensions(Long userId);

    List<NoteResponseDTO> getNotes(Long userId);

    UserResponseDTO getProfile(Long userId);
}
