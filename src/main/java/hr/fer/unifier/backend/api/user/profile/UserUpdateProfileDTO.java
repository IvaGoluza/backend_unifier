package hr.fer.unifier.backend.api.user.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateProfileDTO {
  private Optional<String> profileDescription;

  private Optional<List<String>> workArea;
}
