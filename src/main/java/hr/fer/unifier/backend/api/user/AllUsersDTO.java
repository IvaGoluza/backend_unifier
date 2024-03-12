package hr.fer.unifier.backend.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllUsersDTO {
    private List<PersonDTO> volunteers;

    private List<OrganizationDTO> organizations;
}
