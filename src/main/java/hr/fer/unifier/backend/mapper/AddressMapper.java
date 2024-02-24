package hr.fer.unifier.backend.mapper;

import hr.fer.unifier.backend.api.location.AddressRequestDTO;
import hr.fer.unifier.backend.db.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AddressMapper {
    Address toAddress(AddressRequestDTO addressRequestDTO);
}
