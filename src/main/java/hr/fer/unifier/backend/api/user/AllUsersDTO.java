package hr.fer.unifier.backend.api.user;

import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllUsersDTO {
    private List<PersonProfileDTO> volunteers;

    private List<OrganizationProfileDTO> organizations;
}
