package hr.fer.unifier.backend.api.user.profile;

import hr.fer.unifier.backend.enums.UserType;
import lombok.Data;

@Data
public class PersonProfileDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobilePhone;

    private String profileDescription;

    private UserType userType;
}
