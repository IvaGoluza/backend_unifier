package hr.fer.unifier.backend.db;

import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.entity.Deal;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.Sender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealDao extends JpaRepository<Deal, Long> {

  @Modifying
  void deleteByAcceptedFalseAndRequest(Request request);

  Page<Deal> findAllByRequestAndSender(Request request, Sender sender, Pageable pageable);

  Page<Deal> findAllByAdvertAndSender(Advert advert, Sender sender, Pageable pageable);

  Optional<List<Deal>> findAllBySenderIdOrRequest_User(User user, User requestUser);
}
