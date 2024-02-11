package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.deal.NoteResponseDTO;
import hr.fer.unifier.backend.api.deal.RecensionResponseDTO;
import hr.fer.unifier.backend.api.user.UserResponseDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;
import hr.fer.unifier.backend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileResource {

    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getProfile(userId));
    }
    @PutMapping
    public ResponseEntity<Void> updateProfile(@RequestBody UserProfileDTO userProfileDTO) {
        profileService.updateProfile(userProfileDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recessions/{userId}")
    public ResponseEntity<List<RecensionResponseDTO>> getRecensions(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getRecensions(userId));
    }

    @GetMapping("/notes/{userId}")
    public ResponseEntity<List<NoteResponseDTO>> getNotes(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getNotes(userId));
    }

}
