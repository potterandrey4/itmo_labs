package tools;

public class Checker {
    public static String isInArea(double x, double y, double r) {
        // Upper left corner with 1/4 circle of radius R
        if ((x <= 0 && y >= 0) & ( x <= r & y <= r )){
            return "Попал";
        }
        // Bottom left corner with  triangle
        else if ((x <= 0 && y <= 0) & ( (r*r) >= (x * x + y * y) )) {
            return "Попал";
        }
        // Bottom right corner with rectangle
        else if ((x >= 0 && y <= 0) & (y >= x - r)) {
            return "Попал";
        }
        return "Мимо";
    }
}
