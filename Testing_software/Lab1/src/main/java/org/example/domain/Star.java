package org.example.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Star extends CelestialObject {
    private StarSystem starSystem;

    public Star(String name, Color color, double size, Position position, StarSystem starSystem) {
        super(name, color, size, position);
        this.starSystem = starSystem;
    }

    @Override
    public String getDisplayRepresentation() {
        return "Звезда " + getName() + " (" + getColor() + ")";
    }

    public StarSystem getSystem() {
        return starSystem;
    }
}