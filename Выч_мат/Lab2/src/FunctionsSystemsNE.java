import java.math.BigDecimal;
import java.math.MathContext;

public class FunctionsSystemsNE {
    private BigDecimal x;
    private BigDecimal y;
    private static final MathContext mc = new MathContext(30); // Установим точность вычислений

    public FunctionsSystemsNE(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public static String system1ToString() {
        return "\t𝑠𝑖𝑛(𝑦 + 0.5) − 𝑥 = 1" + "\n" + "\t𝑦 + 𝑐𝑜𝑠(𝑥 - 2) = 0";
    }

    public static BigDecimal system1EquationX(BigDecimal x, BigDecimal y) {
        BigDecimal yPlusHalf = y.add(new BigDecimal("0.5"), mc);
        BigDecimal sinYPlusHalf = BigDecimal.valueOf(Math.sin(yPlusHalf.doubleValue()));
        return sinYPlusHalf.subtract(BigDecimal.ONE, mc);
    }

    public static BigDecimal system1EquationY(BigDecimal x, BigDecimal y) {
        BigDecimal xMinusTwo = x.subtract(new BigDecimal("2"), mc);
        BigDecimal cosXMinusTwo = BigDecimal.valueOf(Math.cos(xMinusTwo.doubleValue()));
        return y.negate().subtract(cosXMinusTwo, mc);
    }

    public static String system2ToString() {
        return "\t𝑐𝑜𝑠(𝑦) + 𝑥 = 1.5" + "\n" + "\t2𝑦 − 𝑠𝑖𝑛(𝑥 − 0.5) = 1";
    }

    public static BigDecimal system2EquationX(BigDecimal x, BigDecimal y) {
        BigDecimal cosY = BigDecimal.valueOf(Math.cos(y.doubleValue()));
        return new BigDecimal("1.5").subtract(cosY, mc);
    }

    public static BigDecimal system2EquationY(BigDecimal x, BigDecimal y) {
        BigDecimal xMinusHalf = x.subtract(new BigDecimal("0.5"), mc);
        BigDecimal sinXMinusHalf = BigDecimal.valueOf(Math.sin(xMinusHalf.doubleValue()));
        return BigDecimal.ONE.subtract(sinXMinusHalf, mc).divide(new BigDecimal("2"), mc);
    }
}
