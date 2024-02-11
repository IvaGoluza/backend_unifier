package hr.fer.unifier.backend.db.user;

import hr.fer.unifier.backend.db.user.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationDao extends JpaRepository<Organization, Long> {
}
