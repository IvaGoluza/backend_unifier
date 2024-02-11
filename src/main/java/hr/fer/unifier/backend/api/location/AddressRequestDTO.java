package hr.fer.unifier.backend.api.location;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequestDTO {
    @NotNull
    private String townName;

    @NotNull
    private String postcode;

    @NotNull
    private String streetName;
}
