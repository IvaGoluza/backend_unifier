package hr.fer.unifier.backend.api.user.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserTypeRequestDTO {
    boolean volunteer;

    boolean helpRecipient;
}
