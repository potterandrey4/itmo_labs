package servlets;

import beans.DataListBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/getData")
public class GetDataServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			DataListBean bean;

			String flag = request.getParameter("load&clear");
			if (flag.equals("clear")) {
				request.getSession().removeAttribute("results");
			}

			bean = (DataListBean) request.getSession().getAttribute("results");
			if (bean == null) {
				bean = new DataListBean();
				request.getSession().setAttribute("results", bean);
			}

			// Преобразование объекта DataListBean в JSON
			ObjectMapper objectMapper = new ObjectMapper();

			String jsonResult = objectMapper.writeValueAsString(bean);

			// Отправка JSON клиенту
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(jsonResult);
			out.flush();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}