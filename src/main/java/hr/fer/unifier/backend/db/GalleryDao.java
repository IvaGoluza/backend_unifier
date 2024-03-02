package hr.fer.unifier.backend.db;

import hr.fer.unifier.backend.db.entity.Gallery;
import hr.fer.unifier.backend.db.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GalleryDao extends JpaRepository<Gallery, Long> {
    Optional<List<Gallery>> findAllByUser(User user);
}
