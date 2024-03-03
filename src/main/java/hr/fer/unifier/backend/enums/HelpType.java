package hr.fer.unifier.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HelpType {
  EDUCATION("OBRAZOVANJE"),
  DONATION("DONACIJE"),
  REPAIRS("POPRAVCI"),
  WORKSHOPS("RADIONICE"),
  HEALTH("ZDRAVLJE"),
  PHYSICAL_WORK("FIZIČKI POSLOVI"),
  ENTERTAINMENT("ZABAVA"),
  REST("OSTALO");

  private final String description;
}
