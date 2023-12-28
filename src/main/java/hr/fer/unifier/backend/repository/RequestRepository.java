package hr.fer.unifier.backend.repository;

import hr.fer.unifier.backend.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
  List<Request> findByUserIdAndDeletedFalse(Long userId);
  List<Request> findAllByActiveTrueAndDeletedFalse();

}
