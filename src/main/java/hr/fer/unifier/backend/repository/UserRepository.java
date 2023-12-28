package hr.fer.unifier.backend.repository;


import hr.fer.unifier.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User getByEmail(String email);

}

