package hr.fer.unifier.backend.api.user.register;

import hr.fer.unifier.backend.api.location.AddressRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationRegisterDTO {
    @NotNull
    private UserRegistrationDTO baseUserDetails;

    @NotNull
    private String name;

    @NotNull
    private String oib;

    @NotNull
    private String type;

    @NotNull
    private AddressRequestDTO address;


    private String url;
}
