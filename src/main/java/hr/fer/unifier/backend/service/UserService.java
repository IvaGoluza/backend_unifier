package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.user.AllUsersDTO;
import hr.fer.unifier.backend.api.user.PasswordResetTokenRequestDTO;
import hr.fer.unifier.backend.api.user.ResetPasswordRequestDTO;
import hr.fer.unifier.backend.api.user.UserCardInfoDTO;
import hr.fer.unifier.backend.db.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface UserService {
  AllUsersDTO getAllUsers();

  void changeBlockStatus(Long userId);

  void approveUser(Long userId);

  ResponseEntity<StreamingResponseBody> getUserCertificateOfGoodConduct(Long userId);

  UserCardInfoDTO getUserCardInfo(Long userId);

  User getUserById(Long userId);
  void passwordReset(PasswordResetTokenRequestDTO passwordResetTokenRequestDTO);

  void updatePassword(ResetPasswordRequestDTO resetPasswordRequestDTO);
}
