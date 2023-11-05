package servlets;

import beans.ResultsBean;
import tools.Checker;
import tools.Validator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {
    public static final int SC_UNPROCESSABLE_ENTITY = 422;
    public static final int SC_INTERNAL_SERVER_ERROR = 500;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDateTime startTime = LocalDateTime.now();

        int x = 0;
        double y = 0;
        int r = 0;

        try {
            x = Integer.parseInt(request.getParameter("x"));
            y = Double.parseDouble(request.getParameter("y").replace(",", "."));
            r = Integer.parseInt("r");

            Validator validator = new Validator(x, y, r);

            if (!validator.checkData()) {
                System.out.println("Невалидные данные");
                return;
            }

            String flagIsHit = Checker.isInArea(x, y, r);

            String deltaTime = Duration.between(startTime, LocalDateTime.now()).toString();
            System.out.println("Время выполнения: " + deltaTime + " ms");

            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html");

            PrintWriter writer = response.getWriter();
            writer.println("<tr class=\"text\">\n");
            writer.println("<td class=\"text\">'" + x + " '</td>");
            writer.println("<td class=\"text\">'" + y + " '</td>");
            writer.println("<td class=\"text\">'" + r + " '</td>");
            writer.println("<td class=\"text\">'" +  deltaTime + "'</td>\n");
            writer.println("<td class=\"text\">' . number_format($executionTime, 3) . ' с </td>\n");
            writer.println("<td class=\"text\">' .date(\"H:i:s\",time()) . '</td>\n");
            writer.println("</tr>';");
            writer.close();


        } catch (NumberFormatException e) {
            System.err.println("error");
        }
    }
}