package tools;

public class Checker {
    public static boolean isInArea(double x, double y, double r) {
        if ((x <= 0 && y >= 0) & ( x <= r & y <= r )){
            return true;
        }
        else if ((x <= 0 && y <= 0) & ( (r*r) >= (x * x + y * y) )) {
            return true;
        }
		else if ((x >= 0 && y <= 0) & (y >= x - r)) {
            return true;
        }
        return false;
    }
}
