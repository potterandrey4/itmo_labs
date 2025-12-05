package itmo.soa.barsservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {
    private String x;
    private String y;

    public Coordinates() {
    }

    public Coordinates(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates copyOf(Coordinates source) {
        if (source == null) {
            return null;
        }
        return new Coordinates(source.getX(), source.getY());
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
