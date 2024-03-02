package hr.fer.unifier.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserActionType {
    ONE_TIME(true),
    MULTIPLE(false);

    private final boolean oneTime;
}
