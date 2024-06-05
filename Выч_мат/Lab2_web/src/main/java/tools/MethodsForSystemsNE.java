package tools;

import java.math.BigDecimal;
import java.math.MathContext;

public class MethodsForSystemsNE {
    private static final MathContext mc = new MathContext(30); // Установим точность вычислений

    public static BigDecimal[] methodOfSimpleIterations(int choice, BigDecimal x, BigDecimal y, BigDecimal eps) {
        int iteration = 0;
        BigDecimal xNew, yNew;  // следующее приближение

        if (choice == 1) {
            while (true) {
                xNew = FunctionsSystemsNE.system1EquationX(x, y);
                yNew = FunctionsSystemsNE.system1EquationY(x, y);
                iteration++;
                System.out.printf("Итерация %d: x = %.4f, y = %.4f%n", iteration, xNew, yNew);

                if (xNew.subtract(x, mc).abs(mc).compareTo(eps) <= 0 && yNew.subtract(y, mc).abs(mc).compareTo(eps) <= 0) {
                    break;
                }
                x = xNew;
                y = yNew;
            }
        } else {
            while (true) {
                xNew = FunctionsSystemsNE.system2EquationX(x, y);
                yNew = FunctionsSystemsNE.system2EquationY(x, y);
                iteration++;
                System.out.printf("Итерация %d: x = %.4f, y = %.4f%n", iteration, xNew, yNew);

                if (xNew.subtract(x, mc).abs(mc).compareTo(eps) <= 0 && yNew.subtract(y, mc).abs(mc).compareTo(eps) <= 0) {
                    break;
                }
                x = xNew;
                y = yNew;
            }
        }
        return new BigDecimal[]{new BigDecimal(iteration), x, y};
    }
}
