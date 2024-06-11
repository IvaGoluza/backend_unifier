package hr.fer.unifier.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DealStatusEnum {
    ACCEPTED, REJECTED, PENDING;

    public static DealStatusEnum getDealStatus(final Boolean accepted){
        if (accepted == null) return PENDING;
        return accepted ? ACCEPTED : REJECTED;
    }
}
