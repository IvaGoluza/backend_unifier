package hr.fer.unifier.backend.api.user;

import hr.fer.unifier.backend.enums.UserType;
import lombok.Data;

@Data
public class PersonDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobilePhone;

    private String volunteerCenter;

    private UserType userType;

    private boolean isBlocked;

    private boolean isApproved;
}
