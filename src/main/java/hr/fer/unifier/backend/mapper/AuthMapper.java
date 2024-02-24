package hr.fer.unifier.backend.mapper;

import hr.fer.unifier.backend.api.user.register.OrganizationRegisterDTO;
import hr.fer.unifier.backend.api.user.register.PersonRegisterDTO;
import hr.fer.unifier.backend.db.user.entity.Organization;
import hr.fer.unifier.backend.db.user.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AddressMapper.class})
public interface AuthMapper {
    @Mapping(target = "userType",ignore = true)
    Organization toOrganization(OrganizationRegisterDTO organizationRegisterDTO);

    @Mapping(target = "userType",ignore = true)
    Person toPerson(PersonRegisterDTO personRegisterDTO);
}
