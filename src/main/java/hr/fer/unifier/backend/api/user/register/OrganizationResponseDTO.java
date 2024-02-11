package hr.fer.unifier.backend.api.user.register;

import hr.fer.unifier.backend.api.location.AddressResponseDTO;
import hr.fer.unifier.backend.api.user.auth.AuthResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationResponseDTO {
    private UserResponseDTO baseInformation;

    private String name;

    private String type;

    private AddressResponseDTO address;

    private String url;

    private AuthResponseDTO auth;
}
