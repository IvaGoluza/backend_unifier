package hr.fer.unifier.backend.model.DTO;
import hr.fer.unifier.backend.validation.EmailValidation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfileDTO {

  private Long id;

  private String profileDescription;

  @NotNull
  private String mobilePhone;
}
