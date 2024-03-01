package hr.fer.unifier.backend.enums;

import lombok.Getter;

@Getter
public enum VolunteerCenter {
    ZAGREB("ZAGREB"),
    SPLIT("SPLIT"),
    RIJEKA("RIJEKA"),
    OSIJEK("OSIJEK"),
    BELISCE("BELŠĆE"),
    DUBROVNIK("DUBROVNIK"),
    SLAVONSKI_BROD("SLAVONSKI BROD"),
    SISAK("SISAK"),
    MEDJIMURJE("MEĐIMURJE");

    private final String name;

    VolunteerCenter(String name){
        this.name = name;
    }
}
