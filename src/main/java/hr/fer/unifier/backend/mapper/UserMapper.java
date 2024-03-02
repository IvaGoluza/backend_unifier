package hr.fer.unifier.backend.mapper;

import hr.fer.unifier.backend.api.user.UserCardInfoDTO;
import hr.fer.unifier.backend.api.user.register.UserResponseDTO;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.db.user.entity.UserCardInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toUserResponseDTO(User user);

    UserCardInfoDTO toUserCardInfo(UserCardInfo userCardInfo);
}
