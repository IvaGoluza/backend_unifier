package hr.fer.unifier.backend.repository;

import hr.fer.unifier.backend.model.Advert;
import hr.fer.unifier.backend.model.enums.Category;
import hr.fer.unifier.backend.model.enums.HelpType;
import hr.fer.unifier.backend.model.enums.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {

  List<Advert> findAdvertsByUserIdAndDeletedFalse(Long userId);
  List<Advert> findAdvertsByDeletedFalse();

}
