package hr.fer.unifier.backend.mapper;

import hr.fer.unifier.backend.api.advert.*;
import hr.fer.unifier.backend.api.deal.AcceptedDealAdvertResponseDTO;
import hr.fer.unifier.backend.api.user.UserCardInfoDTO;
import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {UserMapper.class, CommonMapper.class})
public interface AdvertMapper {
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "user", source = "requestUser")
    @Mapping(target = "category", expression = "java(advertDTO.getCategory().getDescription())")
    @Mapping(target = "helpType", expression = "java(advertDTO.getHelpType().getDescription())")
    Advert toAdvert(AdvertDTO advertDTO, User requestUser);

    @Mapping(target = "volunteerCenter", source = "advert.user.volunteerCenter")
    @Mapping(target = "archived", source = "deleted")
    AdvertResponseDTO toAdvertResponseDTO(Advert advert);


    default AdvertsInfoDTO toAdvertsInfoDTO(Advert advert){
        final AdvertsInfoDTO advertsInfoDTO = new AdvertsInfoDTO();

        advertsInfoDTO.setAdvertId(advert.getAdvertId());
        advertsInfoDTO.setAdvertTitle(advert.getAdvertTitle());
        advertsInfoDTO.setCategory(advert.getCategory());
        advertsInfoDTO.setHelpType(advert.getHelpType());
        advertsInfoDTO.setArchived(advert.getDeleted());

        return advertsInfoDTO;
    }


    @Mapping(target = "volunteerCenter", source = "advert.user.volunteerCenter")
    AcceptedDealAdvertResponseDTO toAcceptedDealAdvertResponseDTO(Advert advert);

    @Mapping(target = "volunteerCenter", source = "advert.user.volunteerCenter")
    @Mapping(target = "archived", source = "deleted")
    @Mapping(target = "helperVolunteers", ignore = true)
    MyAdvertResponse toMyAdvertResponse(Advert advert);

    MyAdvertResponse.UserHelperVolunteerDTO toUserHelperVolunteerDTO(UserCardInfoDTO userCardInfoDTO);

    AdvertsTitlesDTO toAdvertTitlesDTO(Advert advert);
}
