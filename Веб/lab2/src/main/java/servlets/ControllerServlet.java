package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ControllerServlet", urlPatterns = "/controller")
public class ControllerServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getParameter("x") != null && request.getParameter("y") != null && request.getParameter("r") != null) {
				request.getRequestDispatcher("/check").forward(request, response);
			} else {
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}

		} catch (ServletException | IOException e) {
			System.err.println(e);
		}
	}
}