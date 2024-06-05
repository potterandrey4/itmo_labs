package servlets;

import beans.DataBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.FunctionsNE;
import tools.MethodsForNE;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.function.Function;

@WebServlet("/calculateEquation")
public class CalculateEquationServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int choiceEquation = Integer.parseInt(request.getParameter("choiceEquation"));

        double a = Double.parseDouble(request.getParameter("a"));
        double b = Double.parseDouble(request.getParameter("b"));
        double eps = Double.parseDouble(request.getParameter("eps"));

        DataBean bean = new DataBean();

        Function<BigDecimal, BigDecimal> function;
        Function<BigDecimal, BigDecimal> derivativeFunction;
        Function<BigDecimal, BigDecimal> derivativeDerivativeFunction;
        switch (choiceEquation) {
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
                throw new IllegalStateException("Unexpected value: " + choiceEquation);
        }

        BigDecimal[] bisectionMethodRoot = MethodsForNE.bisectionMethod(function, BigDecimal.valueOf(a), BigDecimal.valueOf(b), new BigDecimal(eps));
        System.out.println("метод дихотомии:\n\tитераций = " + bisectionMethodRoot[2] + "\n\tx = " + bisectionMethodRoot[0] + "\n\tf(x) =  " + function.apply(bisectionMethodRoot[0]) );
        bean.setBisectionMethodRoot(bisectionMethodRoot);

        BigDecimal[] chordMethodRoot = MethodsForNE.secantMethod(function, BigDecimal.valueOf(a), BigDecimal.valueOf(b), new BigDecimal(eps));
        System.out.println("метод хорд:\n\tитераций = " + chordMethodRoot[2] + "\n\tx = " + chordMethodRoot[0] + "\n\tf(x) =  " + function.apply(chordMethodRoot[0]) );
        bean.setChordMethod(chordMethodRoot);

        BigDecimal initialApproximation = MethodsForNE.findInitialApproximation(function, derivativeDerivativeFunction, BigDecimal.valueOf(a), BigDecimal.valueOf(b));
        BigDecimal[] newtonMethonRoot = MethodsForNE.newtonMethod(function, derivativeFunction, initialApproximation, new BigDecimal(eps));
        System.out.println("метод Ньютона:\n\tитераций = " + newtonMethonRoot[2] + "\n\tx = " + newtonMethonRoot[0] + "\n\tf(x) =  " + function.apply(newtonMethonRoot[0]) );
        bean.setNewtonMethod(newtonMethonRoot);


        // Преобразование объекта DataListBean в JSON
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonResult = objectMapper.writeValueAsString(bean);

        // Отправка JSON клиенту
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
    }
}