package hr.fer.unifier.backend.db;

import hr.fer.unifier.backend.db.entity.Recension;
import hr.fer.unifier.backend.db.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecensionDao extends JpaRepository<Recension,Long> {
    Optional<List<Recension>> getAllByReviewingUser(User user);
}
