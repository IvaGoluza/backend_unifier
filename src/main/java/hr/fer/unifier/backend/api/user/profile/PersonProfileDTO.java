package hr.fer.unifier.backend.api.user.profile;

import hr.fer.unifier.backend.api.deal.recension.RecensionDTO;
import lombok.Data;

import java.util.List;

@Data
public class PersonProfileDTO {
    private Long id;

    private String name;

    private String email;

    private String mobilePhone;

    private String profileDescription;

    private String image;

    private List<String> workArea;

    private Boolean hasHealthCertificate;

    private Boolean hasCertificateOfGoodConduct;

    private List<RecensionDTO> userRecensions;
}
