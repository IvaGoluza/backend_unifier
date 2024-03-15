package hr.fer.unifier.backend.db.user;

import hr.fer.unifier.backend.db.user.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = "SELECT NEW MyUserDetails(users.email, users.password) FROM User as users WHERE users.email = :email")
    MyUserDetails getUserDetails(String email);


    @Query("SELECT NEW UserWithFile(u.id,u.certificateOfGoodConduct, replace(coalesce(o.name, p.firstName || ' ' || p.lastName),' ', '-'))FROM User as u" +
            " LEFT JOIN Person p ON p.id = u.id" +
            " LEFT JOIN Organization o ON o.id = u.id" +
            " WHERE u.id = :id")
    UserWithFile getUserWithFile(Long id);

    @Query("SELECT NEW UserCardInfo (u.id, coalesce(o.name, p.firstName || ' ' || p.lastName),u.email,u.mobilePhone)FROM User as u" +
            " LEFT JOIN Person p ON p.id = u.id" +
            " LEFT JOIN Organization o ON o.id = u.id" +
            " WHERE u.id = :id")
    UserCardInfo getUserCardInfo(Long id);

    @Query("SELECT NEW UserSearchResults(u.id, COALESCE(o.name, CONCAT(p.firstName, ' ', p.lastName)), u.volunteerCenter) FROM User u" +
            " LEFT JOIN Person p ON p.id = u.id" +
            " LEFT JOIN Organization o ON o.id = u.id" +
            " WHERE u.id <> :id AND u.userType <> 'PERSON_IN_NEED' AND (LOWER(o.name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(CONCAT(p.firstName, ' ', p.lastName)) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<UserSearchResults> getUserVolunteerSearchResult(Long id, String name);
}
