package hr.fer.unifier.backend.service;

import hr.fer.unifier.backend.api.deal.*;
import hr.fer.unifier.backend.util.pagination.UnifierPage;
import org.springframework.data.domain.Pageable;

public interface DealService {

    DealResponseDTO saveDeal(DealDTO dealDTO);

    void updateAccepted(Long dealId);

    void deleteDeal(Long dealId);

    UnifierPage<VolunteerHelpApplicationDTO> getVolunteersHelpApplications(Long requestId, Pageable pageable);

    UnifierPage<PersonInNeedApplicationDTO> getPersonInNeedApplications(Long advertId, Pageable pageable);

    UnifierPage<AcceptedPersonInNeedDealsDTO> getAcceptedDealsForPersonInNeed(Long userId, Pageable pageable);

    UnifierPage<AcceptedDealsVolunteerDTO> getAcceptedDealsForVolunteer(Long userId, Pageable pageable);

    void updateVolunteerDealDescription(Long dealId, VolunteerDealDescriptionDTO volunteerDealDescriptionDTO);

    void rejectDeal(Long dealId);
}
