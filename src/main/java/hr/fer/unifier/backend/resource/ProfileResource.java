package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;
import hr.fer.unifier.backend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProfileResource {

    private final ProfileService profileService;

    @GetMapping("/volunteer/{userId}")
    public ResponseEntity<PersonProfileDTO> getPersonProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getPersonProfile(userId));
    }

    @GetMapping("/organization/{userId}")
    public ResponseEntity<OrganizationProfileDTO> getOrganizationProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getOrganizationProfile(userId));
    }

    @PutMapping
    public ResponseEntity<Void> updateProfile(@RequestBody UserProfileDTO userProfileDTO) {
        profileService.updateProfile(userProfileDTO);
        return ResponseEntity.noContent().build();
    }

}
