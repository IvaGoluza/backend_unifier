package hr.fer.unifier.backend.db.user;

import hr.fer.unifier.backend.db.user.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenDao extends JpaRepository<PasswordResetToken, Long> {
}
