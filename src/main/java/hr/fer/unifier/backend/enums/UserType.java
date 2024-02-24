package hr.fer.unifier.backend.enums;

import hr.fer.unifier.backend.api.user.register.UserTypeRequestDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum UserType {
  VOLUNTEER_AND_PERSON_IN_NEED(new UserTypeRequestDTO(true,true)),
  VOLUNTEER(new UserTypeRequestDTO(true, false)),
  PERSON_IN_NEED(new UserTypeRequestDTO(false, true));

  final UserTypeRequestDTO userTypeRequestDTO;

  final static Map<UserTypeRequestDTO, UserType> lookup = Arrays.stream(values()).collect(Collectors.toMap(UserType::getUserTypeRequestDTO, userType -> userType));
  UserType(UserTypeRequestDTO userTypeRequestDTO){
    this.userTypeRequestDTO = userTypeRequestDTO;
  }

  public static UserType lookup(UserTypeRequestDTO userType) {
    return lookup.get(userType);
  }
}
