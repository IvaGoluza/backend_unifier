package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.user.AllUsersDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface UserService {
  AllUsersDTO getAllUsers();

  void changeBlockStatus(Long userId);

  void approveUser(Long userId);

  ResponseEntity<StreamingResponseBody> getUserCertificateOfGoodConduct(Long userId);
}
