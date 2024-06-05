package tools;

import java.math.BigDecimal;
import java.math.MathContext;

public class FunctionsNE {
    private static final MathContext mc = new MathContext(30); // Установим точность вычислений

    public static BigDecimal function1(BigDecimal x) {
        return x.pow(3, mc)
                .subtract(new BigDecimal("2.561").multiply(x.pow(2, mc), mc), mc)
                .subtract(new BigDecimal("1.325").multiply(x, mc), mc)
                .add(new BigDecimal("4.395"), mc);
    }

    public static String function1ToString() {
        return "x*x*x - 2.561*x*x - 1.325*x + 4.395";
    }

    public static BigDecimal derivativeFunction1(BigDecimal x) {
        return new BigDecimal("3").multiply(x.pow(2, mc), mc)
                .subtract(new BigDecimal("5.122").multiply(x, mc), mc)
                .subtract(new BigDecimal("1.325"), mc);
    }

    public static BigDecimal derivativeDerivativeFunction1(BigDecimal x) {
        return new BigDecimal("6").multiply(x, mc)
                .subtract(new BigDecimal("5.122"), mc);
    }

    public static BigDecimal function2(BigDecimal x) {
        return new BigDecimal("-1.38").multiply(x.pow(3, mc), mc)
                .subtract(new BigDecimal("5.42").multiply(x.pow(2, mc), mc), mc)
                .add(new BigDecimal("2.57").multiply(x, mc), mc)
                .add(new BigDecimal("10.95"), mc);
    }

    public static String function2ToString() {
        return "-1.38*x*x*x - 5.42*x*x + 2.57*x + 10.95";
    }

    public static BigDecimal derivativeFunction2(BigDecimal x) {
        return new BigDecimal("-4.14").multiply(x.pow(2, mc), mc)
                .subtract(new BigDecimal("10.84").multiply(x, mc), mc)
                .add(new BigDecimal("2.57"), mc);
    }

    public static BigDecimal derivativeDerivativeFunction2(BigDecimal x) {
        return new BigDecimal("-8.28").multiply(x, mc)
                .subtract(new BigDecimal("10.84"), mc);
    }

    public static BigDecimal function3(BigDecimal x) {
        return new BigDecimal("-2.4").multiply(x.pow(3, mc), mc)
                .add(new BigDecimal("1.27").multiply(x.pow(2, mc), mc), mc)
                .subtract(new BigDecimal("8.63").multiply(x, mc), mc)
                .add(new BigDecimal("2.31"), mc);
    }

    public static String function3ToString() {
        return "-2.4*x*x*x + 1.27*x*x - 8.63*x + 2.31";
    }

    public static BigDecimal derivativeFunction3(BigDecimal x) {
        return new BigDecimal("-7.2").multiply(x.pow(2, mc), mc)
                .add(new BigDecimal("2.54").multiply(x, mc), mc)
                .subtract(new BigDecimal("8.63"), mc);
    }

    public static BigDecimal derivativeDerivativeFunction3(BigDecimal x) {
        return new BigDecimal("-14.4").multiply(x, mc)
                .add(new BigDecimal("2.54"), mc);
    }

    public static BigDecimal function4(BigDecimal x) {
        return new BigDecimal("-1.8").multiply(x.pow(3, mc), mc)
                .subtract(new BigDecimal("2.94").multiply(x.pow(2, mc), mc), mc)
                .add(new BigDecimal("10.37").multiply(x, mc), mc)
                .add(new BigDecimal("5.38"), mc);
    }

    public static String function4ToString() {
        return "-1.8*x*x*x - 2.94*x*x + 10.37*x + 5.38";
    }

    public static BigDecimal derivativeFunction4(BigDecimal x) {
        return new BigDecimal("-5.4").multiply(x.pow(2, mc), mc)
                .subtract(new BigDecimal("5.88").multiply(x, mc), mc)
                .add(new BigDecimal("10.37"), mc);
    }

    public static BigDecimal derivativeDerivativeFunction4(BigDecimal x) {
        return new BigDecimal("-10.8").multiply(x, mc)
                .subtract(new BigDecimal("5.88"), mc);
    }
}
