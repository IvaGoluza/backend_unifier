package hr.fer.unifier.backend.mapper;


import hr.fer.unifier.backend.api.deal.*;
import hr.fer.unifier.backend.db.entity.Deal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {RequestMapper.class, AdvertMapper.class, CommonMapper.class})
public interface DealMapper {
    @Mapping(target = "senderId",ignore = true)
    Deal toDeal(DealDTO dealDTO);

    DealResponseDTO toDealResponseDTO(Deal deal);

    DealRequestDTO toDealRequestDTO(Deal deal);

    DealAdvertDTO toDealAdvertDTO(Deal deal);

    @Mapping(target = "advert.user", ignore = true)
    VolunteerHelpApplicationDTO toVolunteerHelpApplicationDTO(Deal deal);
    @Mapping(target = "request.user", ignore = true)
    PersonInNeedApplicationDTO toPersonInNeedApplicationDTO(Deal deal);
}
