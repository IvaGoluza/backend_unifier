package hr.fer.unifier.backend.mapper;

import hr.fer.unifier.backend.api.deal.recension.RecensionRequestDTO;
import hr.fer.unifier.backend.db.entity.Recension;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RecensionMapper {
    Recension toRecension(RecensionRequestDTO recensionRequestDTO);
}
