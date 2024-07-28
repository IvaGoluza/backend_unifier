package hr.fer.unifier.backend.db;

import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertDao extends JpaRepository<Advert, Long>, JpaSpecificationExecutor<Advert> {

  Page<Advert> findAllByUserOrderByAdvertIdDesc(User user, Pageable pageable);
}
