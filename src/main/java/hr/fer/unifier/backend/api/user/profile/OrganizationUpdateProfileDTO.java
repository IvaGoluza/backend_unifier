package hr.fer.unifier.backend.api.user.profile;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class OrganizationUpdateProfileDTO {
    private Optional<String> profileDescription;
    private Optional<String> url;
    private Optional<List<String>> workArea;
}
