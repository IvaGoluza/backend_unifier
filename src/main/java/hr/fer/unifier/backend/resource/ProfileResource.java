package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.OrganizationUpdateProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
import hr.fer.unifier.backend.api.user.profile.UserUpdateProfileDTO;
import hr.fer.unifier.backend.api.user.profile.gallery.GalleryDTO;
import hr.fer.unifier.backend.api.user.profile.gallery.GalleryRequestDTO;
import hr.fer.unifier.backend.service.GalleryService;
import hr.fer.unifier.backend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProfileResource {

    private final ProfileService profileService;

    private final GalleryService galleryService;

    @GetMapping("/volunteer/{userId}")
    public ResponseEntity<PersonProfileDTO> getPersonProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getPersonProfile(userId));
    }

    @GetMapping("/organization/{userId}")
    public ResponseEntity<OrganizationProfileDTO> getOrganizationProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getOrganizationProfile(userId));
    }

    @GetMapping("/user-gallery/{userId}")
    public ResponseEntity<List<GalleryDTO>> getProfileGallery(@PathVariable Long userId) {
        return ResponseEntity.ok(galleryService.getGallery(userId));
    }

    @PostMapping(value = "/user-gallery", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Void>> addToGallery(@RequestPart GalleryRequestDTO gallery, @RequestPart MultipartFile file) {
        galleryService.saveToGallery(gallery,file);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/user-profile/{userId}")
    public ResponseEntity<Void> updateUserProfile(@PathVariable Long userId,@RequestBody UserUpdateProfileDTO userUpdateProfileDTO) {
        profileService.updateUserProfile(userId,userUpdateProfileDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/organization-profile/{userId}")
    public ResponseEntity<Void> updateOrganizationProfile(@PathVariable Long userId,@RequestBody OrganizationUpdateProfileDTO userProfileDTO) {
        profileService.updateOrganizationProfile(userId,userProfileDTO);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(value = "/update-profile-image/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateProfileImage(@PathVariable Long userId, @RequestPart MultipartFile file) {
        profileService.updateProfileImage(userId, file);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update-healthcare-certificate/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateProfile(@PathVariable Long userId, @RequestPart MultipartFile file) {
        profileService.uploadHealthCertificate(userId, file);
        return ResponseEntity.noContent().build();
    }

}
