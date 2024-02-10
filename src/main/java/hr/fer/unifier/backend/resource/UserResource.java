package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.user.UserResponseDTO;
import hr.fer.unifier.backend.api.user.profile.UserProfileDTO;
import hr.fer.unifier.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserResource {

  private final UserService userService;

  @GetMapping("/my-profile/{userId}")
  public ResponseEntity<UserResponseDTO> getProfile(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.getProfile(userId));
  }

  @PutMapping("/my-profile")
  public void updateProfile(@RequestBody UserProfileDTO userProfileDTO) {
    userService.updateProfile(userProfileDTO);
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @PutMapping("/user/{userId}")
  public ResponseEntity<HttpStatus> changeBlockStatus(@PathVariable Long userId) {
    userService.changeBlockStatus(userId);
    return ResponseEntity.ok(HttpStatus.NO_CONTENT);
  }
}
