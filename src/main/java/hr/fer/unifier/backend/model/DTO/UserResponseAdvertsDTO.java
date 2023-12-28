package hr.fer.unifier.backend.model.DTO;

import hr.fer.unifier.backend.model.Advert;
import hr.fer.unifier.backend.model.enums.Role;
import hr.fer.unifier.backend.model.enums.UserType;

import java.util.List;

public class UserResponseAdvertsDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobilePhone;

    private String profileDescription;

    private Role role;

    private UserType userType;

    private String password;

    private boolean blocked;

    private List<Advert> adverts;
}
