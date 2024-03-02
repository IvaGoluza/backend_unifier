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
public class UserUpdateProfileDTO {

  private String email;

  private String mobilePhone;

  private String profileDescription;

  private List<String> workArea;
}
