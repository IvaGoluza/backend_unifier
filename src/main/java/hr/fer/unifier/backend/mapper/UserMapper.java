package hr.fer.unifier.backend.mapper;

import hr.fer.unifier.backend.api.user.OrganizationDTO;
import hr.fer.unifier.backend.api.user.PersonDTO;
import hr.fer.unifier.backend.api.user.UserCardInfoDTO;
import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.OrganizationUpdateProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
import hr.fer.unifier.backend.api.user.profile.UserUpdateProfileDTO;
import hr.fer.unifier.backend.api.user.register.UserResponseDTO;
import hr.fer.unifier.backend.db.user.entity.Organization;
import hr.fer.unifier.backend.db.user.entity.Person;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.db.user.entity.UserCardInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AddressMapper.class, CommonMapper.class})
public interface UserMapper {
    UserResponseDTO toUserResponseDTO(User user);

    UserCardInfoDTO toUserCardInfo(UserCardInfo userCardInfo);

    PersonProfileDTO toPersonProfileDTO(Person person);
    OrganizationProfileDTO toOrganizationProfileDTO(Organization organization);

    void updatePerson(@MappingTarget Person person, UserUpdateProfileDTO userUpdateProfileDTO);

    void updateOrganization(@MappingTarget Organization organization, OrganizationUpdateProfileDTO userProfileDTO);

    OrganizationDTO toOrganizationDTO(Organization organization);

    PersonDTO toPersonDTO(Person person);
}
