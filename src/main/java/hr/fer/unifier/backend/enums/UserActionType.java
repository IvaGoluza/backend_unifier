package hr.fer.unifier.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserActionType {
    ONE_TIME(true, "Jednokratna akcija"),
    MULTIPLE(false, "Višekratna akcija");

    private final boolean oneTime;
    private final String actionDescription;

    public static String getActionDescription(boolean oneTime){
        return oneTime ? ONE_TIME.getActionDescription() : MULTIPLE.getActionDescription();
    }
}
