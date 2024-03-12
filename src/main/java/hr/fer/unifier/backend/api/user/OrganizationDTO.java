package hr.fer.unifier.backend.api.user;

import lombok.Data;

@Data
public class OrganizationDTO {
    private Long id;

    private String name;

    private String type;

    private String oib;

    private String email;

    private String mobilePhone;

    private String volunteerCenter;

    private String userType;

    private boolean isBlocked;

    private boolean isApproved;
}
