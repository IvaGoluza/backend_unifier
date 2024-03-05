package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.api.user.AllUsersDTO;
import hr.fer.unifier.backend.api.user.PasswordResetTokenRequestDTO;
import hr.fer.unifier.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


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

  @GetMapping(value = "/user-certificate-of-good-conduct/{userId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<StreamingResponseBody> getUserCertificateOfGoodConduct(@PathVariable Long userId) {
    return userService.getUserCertificateOfGoodConduct(userId);
  }

  @PostMapping(value = "/password-recovery")
  public ResponseEntity<Void> passwordRecovery(@RequestBody PasswordResetTokenRequestDTO passwordResetTokenRequestDTO) {
    userService.passwordReset(passwordResetTokenRequestDTO);
    return ResponseEntity.ok().build();
  }
}
