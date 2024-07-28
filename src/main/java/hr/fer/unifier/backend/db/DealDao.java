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
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealDao extends JpaRepository<Deal, Long> {

  @Modifying
  void deleteByAcceptedFalseAndRequest(Request request);

  Page<Deal> findAllByRequestAndSender(Request request, Sender sender, Pageable pageable);

  Page<Deal> findAllByAdvertAndSender(Advert advert, Sender sender, Pageable pageable);

  Optional<List<Deal>> findAllBySenderIdOrRequest_UserOrderByDealIdDesc(User user, User requestUser);
  Optional<List<Deal>> findAllBySenderIdOrAdvert_UserOrderByDealIdDesc(User user, User advertUser);

  @Query("SELECT d FROM Deal d where d.advert = :advert AND (d.senderId = :user or d.receiver = :user)")
  Optional<Deal> findDealByAdvert(Advert advert, User user);
  @Query("SELECT d FROM Deal d where d.request = :request AND (d.senderId = :user or d.receiver = :user)")
  Optional<Deal> findDealByRequest(Request request, User user);
}
