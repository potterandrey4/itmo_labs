package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ControllerServlet", urlPatterns = "/lab2/controller")
public class ControllerServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost called");  // Добавьте этот вывод для отладки
		try {
			if (request.getParameter("choiceEquation") != null) {
				request.getRequestDispatcher("/calculateEquation").forward(request, response);
			} else if (request.getParameter("choiceSystemEquations") != null) {
				request.getRequestDispatcher("/calculateEquationsSystem").forward(request, response);
			} else {
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		} catch (ServletException | IOException e) {
			System.err.println(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet called");
	}
}


