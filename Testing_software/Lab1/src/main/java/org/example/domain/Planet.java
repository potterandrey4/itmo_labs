package org.example.domain;

import lombok.Getter;

public class Planet extends CelestialObject {
    @Getter
    private final PlanetShape shape;
    private final boolean hasNightSide;

    public Planet(String name, Color color, double size, Position position,
                  PlanetShape shape, boolean hasNightSide) {
        super(name, color, size, position);
        this.shape = shape;
        this.hasNightSide = hasNightSide;
    }

	public boolean hasNightSide() {
        return hasNightSide;
    }

    @Override
    public String getDisplayRepresentation() {
        StringBuilder representation = new StringBuilder();
        representation.append("Планета ")
                .append(getName())
                .append(" цвета ")
                .append(getColor())
                .append(", форма: ")
                .append(shape);

        if (hasNightSide) {
            representation.append(" (видна ночная сторона)");
        }

        return representation.toString();
    }

}
