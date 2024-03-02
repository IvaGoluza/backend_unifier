package hr.fer.unifier.backend.api.user.profile;

import hr.fer.unifier.backend.api.deal.recension.RecensionDTO;
import hr.fer.unifier.backend.api.location.AddressResponseDTO;
import hr.fer.unifier.backend.enums.UserType;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationProfileDTO {
    private Long id;

    private String name;

    private String oib;

    private String type;

    private String email;

    private String mobilePhone;

    private String profileDescription;

    private AddressResponseDTO address;

    private UserType userType;

    private String url;

    private String image;

    private List<String> workArea;

    private List<RecensionDTO> userRecensions;
}
