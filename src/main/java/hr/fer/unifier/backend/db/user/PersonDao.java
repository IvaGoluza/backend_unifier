package hr.fer.unifier.backend.db.user;

import hr.fer.unifier.backend.db.user.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends JpaRepository<Person, Long> {
}
