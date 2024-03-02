package hr.fer.unifier.backend.api.user.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfileDTO {

  private Long userId;

  private String profileDescription;

  private String mobilePhone;

  private List<String> workArea;
}
