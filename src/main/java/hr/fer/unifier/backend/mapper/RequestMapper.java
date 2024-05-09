package hr.fer.unifier.backend.mapper;

import hr.fer.unifier.backend.api.deal.AcceptedDealRequestResponseDTO;
import hr.fer.unifier.backend.api.request.RequestDTO;
import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.api.request.RequestsInfoDTO;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

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

    default RequestsInfoDTO toRequestsInfoDTO(Request request){
        final RequestsInfoDTO requestsInfoDTO = new RequestsInfoDTO();

        requestsInfoDTO.setRequestId(request.getRequestId());
        requestsInfoDTO.setRequestTitle(request.getRequestTitle());
        requestsInfoDTO.setCategory(request.getCategory());
        requestsInfoDTO.setHelpType(request.getHelpType());
        requestsInfoDTO.setArchived(request.getDeleted() || !request.getActive());

        return requestsInfoDTO;
    }

    @Mapping(target = "volunteerCenter", source = "request.user.volunteerCenter")
    @Mapping(target = "typeOfAction", expression = "java(hr.fer.unifier.backend.enums.UserActionType.getActionDescription(request.getOneTime()))")
    AcceptedDealRequestResponseDTO toAcceptedDealRequestResponseDTO(Request request);
}
