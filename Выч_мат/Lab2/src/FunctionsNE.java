public class FunctionsNE {
    public static double function1(double x) {
        return Math.pow(x, 3) - 2.561*Math.pow(x, 2) - 1.325*x + 4.395;
    }
    public static String function1ToString() {
        return "x*x*x - 2.561*x*x - 1.325*x + 4.395";
    }
    public static double derivativeFunction1(double x) {
        return 3*x*x - 5.122*x - 1.325;
    }
    public static double derivativeDerivativeFunction1(double x) {
        return 6*x - 5.122;
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
        return -2.4*x*x*x + 1.27*x*x - 8.63*x + 2.31;
    }
    public static String function3ToString() {
        return "-2.4*x*x*x + 1.27*x*x - 8.63*x + 2.31";
    }
    public static double derivativeFunction3(double x) {
        return -7.2*x*x + 2.54*x - 8.63;
    }
    public static double derivativeDerivativeFunction3(double x) {
        return -14.4*x + 2.54;
    }

    public static double function4(double x) {
        return -1.8*x*x*x - 2.94*x*x + 10.37*x + 5.38;
    }
    public static String function4ToString() {
        return "-1.8*x*x*x - 2.94*x*x + 10.37*x + 5.38";
    }
    public static double derivativeFunction4(double x) {
        return -5.4*x*x - 5.88*x + 10.37;
    }
    public static double derivativeDerivativeFunction4(double x) {
        return -10.8*x - 5.88;
    }
}
