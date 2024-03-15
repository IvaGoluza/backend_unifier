package hr.fer.unifier.backend.mapper;

import hr.fer.unifier.backend.api.user.OrganizationDTO;
import hr.fer.unifier.backend.api.user.PersonDTO;
import hr.fer.unifier.backend.api.user.UserCardInfoDTO;
import hr.fer.unifier.backend.api.user.UserSearchResultsDTO;
import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
import hr.fer.unifier.backend.api.user.register.UserResponseDTO;
import hr.fer.unifier.backend.db.user.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AddressMapper.class, CommonMapper.class})
public interface UserMapper {
    UserResponseDTO toUserResponseDTO(User user);
    UserCardInfoDTO toUserCardInfo(UserCardInfo userCardInfo);
    PersonProfileDTO toPersonProfileDTO(Person person);
    OrganizationProfileDTO toOrganizationProfileDTO(Organization organization);
    OrganizationDTO toOrganizationDTO(Organization organization);
    PersonDTO toPersonDTO(Person person);

    UserSearchResultsDTO toUserSearchResultsDTO(UserSearchResults userSearchResults);
}
