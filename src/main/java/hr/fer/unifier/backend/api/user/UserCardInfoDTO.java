package hr.fer.unifier.backend.api.user;

import lombok.Data;

@Data
public class UserCardInfoDTO {
    private Long userId;
    private String name;
    private String email;
    private String number;
}
