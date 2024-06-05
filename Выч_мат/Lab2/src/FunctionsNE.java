public class FunctionsNE {
    public static double function1(double x) {
            return 2.74*x*x*x - 1.93*x*x - 15.28*x -3.72;
    }
    public static String function1ToString() {
            return "2.74*x*x*x - 1.93*x*x - 15.28*x -3.72";
    }
    public static double derivativeFunction1(double x) {
            return 8.22*x*x - 3.86*x - 15.28;
    }
    public static double derivativeDerivativeFunction1(double x) {
            return 16.44*x - 3.68;
    }

    public static double function2(double x) {
        return -1.38*x*x*x - 5.42*x*x + 2.57*x + 10.95;
    }
    public static String function2ToString() {
        return "-1.38*x*x*x - 5.42*x*x + 2.57*x + 10.95";
    }
    public static double derivativeFunction2(double x) {
        return -4.14*x*x - 10.84*x + 2.57;
    }
    public static double derivativeDerivativeFunction2(double x) {
        return -8.28*x - 10.84;
    }

    public static double function3(double x) {
        return x*x*x + 2.84*x*x - 5.606*x - 14.766;
    }
    public static String function3ToString() {
        return "x*x*x + 2.84*x*x - 5.606*x - 14.766";
    }
    public static double derivativeFunction3(double x) {
        return 3*x*x + 5.68*x - 5.606;
    }
    public static double derivativeDerivativeFunction3(double x) {
        return 6*x - 5.68;
    }

    public static double function4(double x) {
        return x*x*x - 1.89*x*x - 2*x + 1.76;
    }
    public static String function4ToString() {
        return "x*x*x - 1.89*x*x - 2*x + 1.76";
    }
    public static double derivativeFunction4(double x) {
        return 3*x*x - 3.78*x - 2;
    }
    public static double derivativeDerivativeFunction4(double x) {
        return 6*x - 3.78;
    }
}
