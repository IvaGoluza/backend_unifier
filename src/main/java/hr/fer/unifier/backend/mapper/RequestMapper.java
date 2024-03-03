package hr.fer.unifier.backend.mapper;

import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestInfoDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.api.request.RequestsInfoDTO;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {UserMapper.class})
public interface RequestMapper {
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "user", source = "requestUser")
    @Mapping(target = "category", expression = "java(requestDTO.getCategory().getDescription())")
    @Mapping(target = "helpType", expression = "java(requestDTO.getHelpType().getDescription())")
    @Mapping(target = "oneTime", expression = "java(requestDTO.getTypeOfAction().isOneTime())")
    Request toRequest(RequestDTO requestDTO, User requestUser);

    @Mapping(target = "volunteerCenter", source = "request.user.volunteerCenter")
    @Mapping(target = "archived", source = "deleted")
    RequestResponseDTO toRequestResponseDTO(Request request);
    RequestInfoDTO toRequestInfoDTO(Request request);

    default RequestsInfoDTO toRequestsInfoDTO(List<Request> requests){
        List<RequestInfoDTO> active = requests
                .stream()
                .filter(request -> !request.getDeleted())
                .map(this::toRequestInfoDTO)
                .toList();

        List<RequestInfoDTO> archived = requests
                .stream()
                .filter(Request::getDeleted)
                .map(this::toRequestInfoDTO)
                .toList();

        return new RequestsInfoDTO(active,archived);
    }
}
