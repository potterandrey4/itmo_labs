package servlets;

import beans.DataListBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.Checker;
import tools.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		LocalDateTime startTime = LocalDateTime.now();

		double[] x;
		double y;
		int r;

		try {
			String[] xValuesArray = request.getParameter("x").split(";");

			x = new double[xValuesArray.length];
			for (int i = 0; i < xValuesArray.length; i++) {
				x[i] = Double.parseDouble(xValuesArray[i]);
			}

			y = Double.parseDouble(request.getParameter("y").replace(",", "."));
			r = Integer.parseInt(request.getParameter("r"));

			// формирование bean компонента
			DataListBean bean = (DataListBean) request.getSession().getAttribute("results");
			if (bean == null) {
				bean = new DataListBean();
				request.getSession().setAttribute("results", bean);
			}

			for (int i = 0; i < x.length; i++) {

				Validator validator = new Validator(x[i], y, r);

				if (!validator.checkData()) {
					System.out.println("Невалидные данные");
					return;
				}

				boolean flagIsHit = Checker.isInArea(x[i], y, r);

				String executionTime = String.valueOf(Duration.between(startTime, LocalDateTime.now()).toMillis());

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
				DataListBean.DataBean result = new DataListBean.DataBean(x[i], y, r, flagIsHit, executionTime, LocalDateTime.now().format(formatter));

				bean.add(result);
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

		} catch (NumberFormatException | IOException e) {
			System.err.println(e.toString());
		}
	}
}