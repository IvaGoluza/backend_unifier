package hr.fer.unifier.backend.db;

import hr.fer.unifier.backend.db.entity.Gallery;
import hr.fer.unifier.backend.db.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryDao extends JpaRepository<Gallery, Long> {
    Page<Gallery> findAllByUser(User user, Pageable pageable);
}
