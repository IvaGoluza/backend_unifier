package hr.fer.unifier.backend.api.user;

import hr.fer.unifier.backend.api.request.RequestResponseDTO;
import hr.fer.unifier.backend.enums.Role;
import hr.fer.unifier.backend.enums.UserType;

import java.util.List;

public class UserResponseRequestsDTO {

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

    private List<RequestResponseDTO> requests;
}
