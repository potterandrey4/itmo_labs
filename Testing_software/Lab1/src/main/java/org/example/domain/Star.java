package org.example.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Star extends CelestialObject {
    private StarSystem starSystem;

    public Star(Name name, Color color, double size, Position position, StarSystem starSystem) {
        super(name, color, size, position);
        this.starSystem = starSystem;
    }

    @Override
    protected boolean isStarObject() {
        return true;
    }

    public StarSystem getSystem() {
        return starSystem;
    }
}