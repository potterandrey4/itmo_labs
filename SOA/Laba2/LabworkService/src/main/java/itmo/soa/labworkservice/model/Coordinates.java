package itmo.soa.labworkservice.model;

import jakarta.validation.constraints.NotBlank;

public class Coordinates {
    @NotBlank(message = "Coordinate x is required")
    private String x;

    @NotBlank(message = "Coordinate y is required")
    private String y;

    public Coordinates() {
    }

    public Coordinates(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
