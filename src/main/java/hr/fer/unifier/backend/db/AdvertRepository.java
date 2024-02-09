package hr.fer.unifier.backend.db;

import hr.fer.unifier.backend.db.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {

  List<Advert> findAdvertsByUserIdAndDeletedFalse(Long userId);
  List<Advert> findAdvertsByDeletedFalse();

}
