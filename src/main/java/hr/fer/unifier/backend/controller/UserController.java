package hr.fer.unifier.backend.controller;

import hr.fer.unifier.backend.model.DTO.*;
import hr.fer.unifier.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/registration")
  public ResponseEntity<UserResponseDTO> saveUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDto) {
    return ResponseEntity.ok(userService.saveUser(userRegistrationDto));
  }

  @PostMapping("/login")
  public ResponseEntity<UserResponseDTO> checkUser(@RequestBody @Valid UserLoginDTO userLoginDTO) {
    return ResponseEntity.ok(userService.checkUser(userLoginDTO));
  }

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
