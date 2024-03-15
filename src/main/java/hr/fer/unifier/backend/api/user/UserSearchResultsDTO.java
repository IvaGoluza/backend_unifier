package hr.fer.unifier.backend.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResultsDTO {
    private Long userId;

    private String name;

    private String volunteerCenter;
}