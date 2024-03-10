package hr.fer.unifier.backend.db;

import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestDao extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {
  Page<Request> findAllByActiveTrueAndDeletedFalse(@Nullable Specification<Request> spec, Pageable pageable);

  Page<Request> findAllByUserOrderByRequestIdDesc(User user, Pageable pageable);
}
