package hr.fer.unifier.backend.repository;

import hr.fer.unifier.backend.model.Deal;
import hr.fer.unifier.backend.model.enums.Sender;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

  @Transactional
  void deleteByAcceptedFalseAndRequest_Id(Long requestId);
  List<Deal> findDealsByAcceptedFalseAndAdvert_User_IdAndSender(Long userId, Sender sender);
  List<Deal> findDealsByAcceptedFalseAndRequest_IdAndSender(Long requestId, Sender sender);
  List<Deal> findDealsByAdvert_User_IdAndSenderAndAcceptedTrueOrSenderAndAdvert_User_Id(Long userId, Sender sender, Sender sender2, Long userId2);
  List<Deal> findDealsByRequest_User_IdAndSenderAndAcceptedTrueOrSenderAndRequest_User_Id(Long userId, Sender sender, Sender sender2, Long userId2);
  List<Deal> findDealsByAdvert_User_IdAndRecensionNotNull(Long userId);
  List<Deal> findDealsByRequest_User_IdAndNoteNotNull(Long userId);
}
