package hr.fer.unifier.backend.db.user;

import hr.fer.unifier.backend.api.user.MyUserDetails;
import hr.fer.unifier.backend.db.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query(value = "SELECT NEW MyUserDetails(users.email, users.password) FROM User as users WHERE users.email = :email")
    MyUserDetails getUserDetails(String email);
}
