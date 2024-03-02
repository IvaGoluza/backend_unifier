package hr.fer.unifier.backend.db;

import hr.fer.unifier.backend.db.entity.Recension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecensionDao extends JpaRepository<Recension,Long> {
}
