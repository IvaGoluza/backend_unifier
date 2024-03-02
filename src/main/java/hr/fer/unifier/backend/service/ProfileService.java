package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;

public interface ProfileService {
    void updateProfile(UserProfileDTO userProfileDTO);
    PersonProfileDTO getPersonProfile(Long userId);
    OrganizationProfileDTO getOrganizationProfile(Long userId);
}
