import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    private static final MathContext mc = new MathContext(30); // Установим точность вычислений

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите режим:\n\t1) нелинейное уравнение\n\t2) система нелинейных уравнений");

        int modeChoice = scanner.nextInt();

        if (modeChoice == 1) {

            System.out.println("Выберите функцию:");
            System.out.println("1: " + FunctionsNE.function1ToString());
            System.out.println("2: " + FunctionsNE.function2ToString());
            System.out.println("3: " + FunctionsNE.function3ToString());
            System.out.println("4: " + FunctionsNE.function4ToString());
            int functionChoice = scanner.nextInt();
            while (functionChoice != 1 && functionChoice != 2 && functionChoice != 3 && functionChoice != 4) {
                System.out.println("Некорректный выбор. Введите 1, 2, 3 или 4:");
                functionChoice = scanner.nextInt();
            }

            Function<BigDecimal, BigDecimal> function;
            Function<BigDecimal, BigDecimal> derivativeFunction;
            Function<BigDecimal, BigDecimal> derivativeDerivativeFunction;
            switch (functionChoice) {
                case 1:
                    function = FunctionsNE::function1;
                    derivativeFunction = FunctionsNE::derivativeFunction1;
                    derivativeDerivativeFunction = FunctionsNE::derivativeDerivativeFunction1;
                    break;
                case 2:
                    function = FunctionsNE::function2;
                    derivativeFunction = FunctionsNE::derivativeFunction2;
                    derivativeDerivativeFunction = FunctionsNE::derivativeDerivativeFunction2;
                    break;
                case 3:
                    function = FunctionsNE::function3;
                    derivativeFunction = FunctionsNE::derivativeFunction3;
                    derivativeDerivativeFunction = FunctionsNE::derivativeDerivativeFunction3;
                    break;
                case 4:
                    function = FunctionsNE::function4;
                    derivativeFunction = FunctionsNE::derivativeFunction4;
                    derivativeDerivativeFunction = FunctionsNE::derivativeDerivativeFunction4;
                    break;
                default:
                    System.out.println("Неверный выбор функции!");
                    return;
            }

            int a = 1;
            int b = 3;
            DecimalFormat df = new DecimalFormat("##.####################");

            BigDecimal[] bisectionMethodRoot = MethodsForNE.bisectionMethod(function, BigDecimal.valueOf(a), BigDecimal.valueOf(b), new BigDecimal("0.001"));
            System.out.println("метод дихотомии:\n\tитераций = " + bisectionMethodRoot[2] + "\n\tx = " + bisectionMethodRoot[0] + "\n\tf(x) = " + df.format(function.apply(bisectionMethodRoot[0])) );

            BigDecimal[] chordMethodRoot = MethodsForNE.secantMethod(function, BigDecimal.valueOf(a), BigDecimal.valueOf(b), new BigDecimal("0.001"));
            System.out.println("метод хорд:\n\tитераций = " + chordMethodRoot[2] + "\n\tx = " + df.format(chordMethodRoot[0]) + "\n\tf(x) = " + df.format(function.apply(chordMethodRoot[0])) );

            BigDecimal initialApproximation = MethodsForNE.findInitialApproximation(function, derivativeDerivativeFunction, BigDecimal.valueOf(a), BigDecimal.valueOf(b));
            BigDecimal[] newtonMethonRoot = MethodsForNE.newtonMethod(function, derivativeFunction, initialApproximation, new BigDecimal("0.001"));
            System.out.println("метод Ньютона:\n\tитераций = " + newtonMethonRoot[2] + "\n\tx = " + df.format(newtonMethonRoot[0]) + "\n\tf(x) = " + df.format(function.apply(newtonMethonRoot[0])) );

        } else if (modeChoice == 2) {

            System.out.println("Выберите систему нелинейных уравнений:");
            System.out.println("1\n" + FunctionsSystemsNE.system1ToString() + "\n");
            System.out.println("2\n" + FunctionsSystemsNE.system2ToString());

            int choice = scanner.nextInt();

            BigDecimal epsilon = new BigDecimal("0.02");
            BigDecimal x = new BigDecimal("1.0");
            BigDecimal y = new BigDecimal("2.0"); // первое (текущее) приближение


            while (choice != 1 && choice != 2) {
                System.out.println("Некорректный выбор. Введите 1 или 2:");
                choice = scanner.nextInt();
            }

            BigDecimal[] simpleIterationsRoot = MethodsForSystemsNE.methodOfSimpleIterations(choice, x, y, epsilon);
            System.out.printf("Решение найдено за %.0f итераций: x = %.4f, y = %.4f%n", simpleIterationsRoot[0], simpleIterationsRoot[1], simpleIterationsRoot[2]);

        }

    }
}
