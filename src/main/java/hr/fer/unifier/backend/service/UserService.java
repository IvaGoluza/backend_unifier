package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.user.*;
import hr.fer.unifier.backend.db.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

public interface UserService {
    AllUsersDTO getAllUsers();

    void changeBlockStatus(Long userId);

    void approveUser(Long userId);

    ResponseEntity<StreamingResponseBody> getUserCertificateOfGoodConduct(Long userId);

    UserCardInfoDTO getUserCardInfo(Long userId);

    User getUserById(Long userId);

    void passwordReset(PasswordResetTokenRequestDTO passwordResetTokenRequestDTO);

    void updatePassword(ResetPasswordRequestDTO resetPasswordRequestDTO);

    List<UserSearchResultsDTO> searchVolunteers(Long userId, String name);
}
