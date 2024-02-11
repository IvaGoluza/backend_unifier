package hr.fer.unifier.backend.db;

import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertDao extends JpaRepository<Advert, Long> {

  Optional<List<Advert>> findByUserAndDeletedFalse(User user);
  Optional<List<Advert>> findAdvertsByDeletedFalse();

}
