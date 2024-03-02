package hr.fer.unifier.backend.mapper;


import hr.fer.unifier.backend.api.deal.DealDTO;
import hr.fer.unifier.backend.api.deal.DealResponseDTO;
import hr.fer.unifier.backend.api.deal.PersonInNeedApplicationDTO;
import hr.fer.unifier.backend.api.deal.VolunteerHelpApplicationDTO;
import hr.fer.unifier.backend.db.entity.Deal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CommonMapper.class,RequestMapper.class, AdvertMapper.class})
public interface DealMapper {
    @Mapping(target = "senderId",ignore = true)
    Deal toDeal(DealDTO dealDTO);
    DealResponseDTO toDealResponseDTO(Deal deal);
    VolunteerHelpApplicationDTO toVolunteerHelpApplicationDTO(Deal deal);
    PersonInNeedApplicationDTO toPersonInNeedApplicationDTO(Deal deal);

}
