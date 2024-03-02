package hr.fer.unifier.backend.api.user.profile;

import hr.fer.unifier.backend.api.location.AddressRequestDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationUpdateProfileDTO {
    private String name;
    private String profileDescription;
    private String email;
    private String mobilePhone;
    private AddressRequestDTO address;
    private String type;
    private String url;
    private String oib;
    private List<String> workArea;
}
