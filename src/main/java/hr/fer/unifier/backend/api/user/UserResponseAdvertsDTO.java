package hr.fer.unifier.backend.api.user;

import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.enums.Role;
import hr.fer.unifier.backend.enums.UserType;

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
