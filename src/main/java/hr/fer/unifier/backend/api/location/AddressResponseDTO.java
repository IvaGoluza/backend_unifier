package hr.fer.unifier.backend.api.location;

import lombok.Data;

@Data
public class AddressResponseDTO {
    private String townName;

    private String postcode;

    private String streetName;
}
