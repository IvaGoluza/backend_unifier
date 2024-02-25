package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.user.AllUsersDTO;
import hr.fer.unifier.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserResource {

  private final UserService userService;

  @GetMapping("/all-users")
  public ResponseEntity<AllUsersDTO> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @PutMapping("/change-block-status/{userId}")
  public ResponseEntity<Void> changeBlockStatus(@PathVariable Long userId) {
    userService.changeBlockStatus(userId);
    return ResponseEntity.noContent().build();
  }
  @PutMapping("/approve-user/{userId}")
  public ResponseEntity<Void> approveUser(@PathVariable Long userId) {
    userService.approveUser(userId);
    return ResponseEntity.noContent().build();
  }
}
