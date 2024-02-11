package hr.fer.unifier.backend.api.user.login;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserLoginDTO {

  @NotNull
  private String email;

  @NotNull
  private String password;

}
