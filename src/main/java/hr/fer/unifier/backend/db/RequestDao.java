package hr.fer.unifier.backend.db;

import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RequestDao extends JpaRepository<Request, Long> {
  Optional<List<Request>> findByUserAndDeletedFalse(User user);
  Optional<List<Request>> findAllByActiveTrueAndDeletedFalse();

}
