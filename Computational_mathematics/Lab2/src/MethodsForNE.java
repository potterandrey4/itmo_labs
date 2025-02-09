import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.Function;

public class MethodsForNE {
    private static final MathContext mc = new MathContext(30); // Установим точность вычислений

    public static BigDecimal[] bisectionMethod(Function<BigDecimal, BigDecimal> function, BigDecimal a, BigDecimal b, BigDecimal eps) {
        BigDecimal[] result = new BigDecimal[3];
        int iterations = 0;
        BigDecimal mid = a.add(b, mc).divide(new BigDecimal("2"), mc);

        while (b.subtract(a, mc).abs(mc).compareTo(eps) > 0) {
            mid = a.add(b, mc).divide(new BigDecimal("2"), mc);
            BigDecimal fMid = function.apply(mid);

            if (fMid.abs(mc).compareTo(eps) < 0) {
                break;
            }

            if (function.apply(a).multiply(fMid, mc).compareTo(BigDecimal.ZERO) < 0) {
                b = mid;
            } else {
                a = mid;
            }
            iterations++;
        }

        result[0] = mid;
        result[1] = function.apply(mid);
        result[2] = new BigDecimal(iterations);

        return result;
    }

    public static BigDecimal[] secantMethod(Function<BigDecimal, BigDecimal> function, BigDecimal a, BigDecimal b, BigDecimal eps) {
        BigDecimal[] result = new BigDecimal[3];
        BigDecimal fa = function.apply(a);
        BigDecimal fb = function.apply(b);
        int iterations = 0;
        BigDecimal c = BigDecimal.ZERO;

        while (b.subtract(a, mc).abs(mc).compareTo(eps) > 0) {
            c = b.subtract(fb.multiply(b.subtract(a, mc), mc).divide(fb.subtract(fa, mc), mc), mc);
            a = b;
            fa = fb;
            b = c;
            fb = function.apply(b);
            iterations++;
        }

        result[0] = c;
        result[1] = function.apply(c);
        result[2] = new BigDecimal(iterations);

        return result;
    }

    public static BigDecimal[] newtonMethod(Function<BigDecimal, BigDecimal> function, Function<BigDecimal, BigDecimal> derivative, BigDecimal x0, BigDecimal eps) {
        BigDecimal[] result = new BigDecimal[3];
        int iterations = 0;
        BigDecimal x = x0;
        BigDecimal fx = function.apply(x);
        BigDecimal dfx;

        while (fx.abs(mc).compareTo(eps) > 0) {
            dfx = derivative.apply(x);
            if (dfx.compareTo(BigDecimal.ZERO) == 0) {
                throw new ArithmeticException("Derivative is zero, method fails.");
            }
            x = x.subtract(fx.divide(dfx, mc), mc);
            fx = function.apply(x);
            iterations++;
        }

        result[0] = x;
        result[1] = fx;
        result[2] = new BigDecimal(iterations);

        return result;
    }

    public static BigDecimal findInitialApproximation(Function<BigDecimal, BigDecimal> function, Function<BigDecimal, BigDecimal> derivative, BigDecimal a, BigDecimal b) {
        BigDecimal fa = function.apply(a);
        BigDecimal fb = function.apply(b);

        if (fa.multiply(fb, mc).compareTo(BigDecimal.ZERO) < 0) {
            return a.add(b, mc).divide(new BigDecimal("2"), mc);
        }

        // If a <= 0, and b > 0, start with a small positive value
        if (a.compareTo(BigDecimal.ZERO) <= 0 && b.compareTo(BigDecimal.ZERO) > 0) {
            return b;
        }

        // Otherwise, use the midpoint
        return a.add(b, mc).divide(new BigDecimal("2"), mc);
    }
}
