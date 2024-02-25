package hr.fer.unifier.backend.db.user;

import hr.fer.unifier.backend.db.user.entity.MyUserDetails;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.db.user.entity.UserWithFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = "SELECT NEW MyUserDetails(users.email, users.password) FROM User as users WHERE users.email = :email")
    MyUserDetails getUserDetails(String email);


    @Query("SELECT NEW UserWithFile(u.id,u.file, replace(coalesce(o.name, p.firstName || ' ' || p.lastName),' ', '-'))FROM User as u" +
            " LEFT JOIN Person p ON p.id = u.id" +
            " LEFT JOIN Organization o ON o.id = u.id" +
            " WHERE u.id = :id")
    UserWithFile getUserWithFile(Long id);
}
