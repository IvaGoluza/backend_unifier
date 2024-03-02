package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.OrganizationUpdateProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
import hr.fer.unifier.backend.api.user.profile.UserUpdateProfileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    void updateProfileImage(Long userId, MultipartFile file);
    void uploadHealthCertificate(Long userId, MultipartFile file);
    void updateUserProfile(Long userId,UserUpdateProfileDTO userUpdateProfileDTO);
    void updateOrganizationProfile(Long userId,OrganizationUpdateProfileDTO userProfileDTO);
    PersonProfileDTO getPersonProfile(Long userId);
    OrganizationProfileDTO getOrganizationProfile(Long userId);
}
