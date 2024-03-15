package hr.fer.unifier.backend.api.advert;

import hr.fer.unifier.backend.api.user.register.UserResponseDTO;
import lombok.Data;

import java.util.Set;

@Data
public class MyAdvertResponse {
    private Long advertId;

    private String advertTitle;

    private String location;

    private String helpType;

    private String category;

    private String description;

    private String advertImage;

    private String volunteerCenter;

    private String time;

    private boolean archived;

    private UserResponseDTO creator;

    private Set<UserHelperVolunteerDTO> helperVolunteers;

    @Data
    public static class UserHelperVolunteerDTO {
        private Long userId;
        private String name;
    }
}
