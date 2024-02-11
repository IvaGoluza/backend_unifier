package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.user.AllUsersDTO;
import hr.fer.unifier.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserResource {

  private final UserService userService;

  @GetMapping("/all-users")
  public ResponseEntity<AllUsersDTO> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @PutMapping("/change-block-status/{userId}")
  public ResponseEntity<HttpStatus> changeBlockStatus(@PathVariable Long userId) {
    userService.changeBlockStatus(userId);
    return ResponseEntity.ok(HttpStatus.NO_CONTENT);
  }
}
