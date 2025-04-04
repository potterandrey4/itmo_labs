package org.example.domain;

public abstract class CelestialObject {
    private final String name;
    private final Color color;
    private final double size;
    private Position position;

    public CelestialObject(String name, Color color, double size, Position position) {
        this.name = name;
        this.color = color;
        this.size = size;
        this.position = position;
    }

    public abstract String getDisplayRepresentation();

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public double getSize() {
        return size;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
