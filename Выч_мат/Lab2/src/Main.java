import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите функцию:");
        System.out.println("1 - function1");
        System.out.println("2 - function2");
        System.out.println("3 - function3");
        System.out.println("4 - function4");
        int functionChoice = scanner.nextInt();

        Function<Double, Double> function;
        switch (functionChoice) {
            case 1:
                function = FunctionsNE::function1;
                break;
            case 2:
                function = FunctionsNE::function2;
                break;
            case 3:
                function = FunctionsNE::function3;
                break;
            case 4:
                function = FunctionsNE::function4;
                break;
            default:
                System.out.println("Неверный выбор функции!");
                return;
        }


        double[] bisectionMethodRoot = MethodsForNE.bisectionMethod(function, 0, 1, 0.01);
        System.out.println("метод дихотомии: "+ bisectionMethodRoot[0] + " | " + bisectionMethodRoot[1] + " | " + bisectionMethodRoot[2]);

        double[] chordMethodRoot = MethodsForNE.chordMethod(function, 0, 1, 0.01);
        System.out.println("метод хорд: "+ chordMethodRoot[0] + " | " + chordMethodRoot[1] + " | " + chordMethodRoot[2]);

    }
}