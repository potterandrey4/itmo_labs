package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.MethodsForSystemsNE;

import java.math.BigDecimal;

@WebServlet("/calculateEquationsSystem")
public class CalculateEquationsSystemServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        int choiceSystemEquations = Integer.parseInt(request.getParameter("choiceSystemEquations"));

        double eps = Double.parseDouble(request.getParameter("eps"));

        BigDecimal epsilon = new BigDecimal(eps);
        BigDecimal x = new BigDecimal("1.0");
        BigDecimal y = new BigDecimal("1.0"); // первое (текущее) приближение



        BigDecimal[] simpleIterationsRoot = MethodsForSystemsNE.methodOfSimpleIterations(choiceSystemEquations, x, y, epsilon);
        System.out.printf("Решение найдено за %.0f итераций: x = %.4f, y = %.4f%n", simpleIterationsRoot[0], simpleIterationsRoot[1], simpleIterationsRoot[2]);

// TODO: 05.06.2024 поменять soutы на упаковку в JSON и отправку его
    }
}