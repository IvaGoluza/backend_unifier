package hr.fer.unifier.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
  CHILDREN_AND_YOUNGER("DJECA I MLAĐI"),
  SENIORS("STARIJI"),
  FAMILY("OBITELJI"),
  HOMELESS("BESKUĆNICI"),
  ADDICTS("ADDICTS"),
  PERSON_WITH_DISABILITIES("OSOBA S INVALIDITETOM"),
  VOLNERABLES("RANJIVE SKUPINE"),
  ANIMALS("ŽIVOTINJE"),
  REST("OSTALO");

  private final String description;

}
