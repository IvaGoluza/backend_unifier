package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    void updateProfileImage(Long userId, MultipartFile file);
    void uploadHealthCertificate(Long userId, MultipartFile file);
    void updateProfile(UserProfileDTO userProfileDTO);
    PersonProfileDTO getPersonProfile(Long userId);
    OrganizationProfileDTO getOrganizationProfile(Long userId);
}
