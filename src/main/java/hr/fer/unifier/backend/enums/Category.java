package hr.fer.unifier.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
  CHILDREN_AND_YOUNGER("DJECA I MLADI"),
  ELDERLY("STARIJI"),
  FAMILY("OBITELJI"),
  HOMELESS("BESKUĆNICI"),
  ADDICTS("OVISINICI"),
  DISABLED("OSOBE S INVALIDITETOM"),
  VULNERABLE_GROUPS("RANJIVE SKUPINE"),
  ANIMALS("ŽIVOTINJE"),
  ENVIRONMENT("OKOLIŠ"),
  REST("OSTALO");

  private final String description;

}
