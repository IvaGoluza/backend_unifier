package hr.fer.unifier.backend.db.user;

import hr.fer.unifier.backend.db.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
