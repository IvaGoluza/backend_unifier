package hr.fer.unifier.backend.db;

import hr.fer.unifier.backend.db.entity.Deal;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.entity.User;
import hr.fer.unifier.backend.enums.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealDao extends JpaRepository<Deal, Long> {

  @Modifying
  void deleteByAcceptedFalseAndRequest(Request request);
  Optional<List<Deal>> findByAcceptedFalseAndAdvert_UserAndSender(User user, Sender sender);
  Optional<List<Deal>> findByAcceptedFalseAndRequestAndSender(Request request, Sender sender);
  Optional<List<Deal>> findByAdvert_UserAndSenderAndAcceptedTrueOrSenderAndAdvert_User(User user, Sender sender, Sender sender2, User user2);
  Optional<List<Deal>> findByRequest_UserAndSenderAndAcceptedTrueOrSenderAndRequest_User(User user, Sender sender, Sender sender2, User user2);
  Optional<List<Deal>> findByAdvert_UserAndRecensionNotNull(User user);
  Optional<List<Deal>> findByRequest_UserAndNoteNotNull(User user);
}
