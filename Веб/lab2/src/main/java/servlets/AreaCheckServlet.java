package servlets;

import beans.DataListBean;
import com.fasterxml.jackson.databind.ObjectMapper;
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

		int x;
		double y;
		int r;

		try {
			x = Integer.parseInt(request.getParameter("x"));
			y = Double.parseDouble(request.getParameter("y").replace(",", "."));
			r = Integer.parseInt(request.getParameter("r"));

			Validator validator = new Validator(x, y, r);

			if (!validator.checkData()) {
				System.out.println("Невалидные данные");
				return;
			}

			boolean flagIsHit = Checker.isInArea(x, y, r);

			String executionTime = String.valueOf(Duration.between(startTime, LocalDateTime.now()).toMillis());

			// формирование bean компонента
			DataListBean bean = (DataListBean) request.getSession().getAttribute("results");
			if (bean == null) {
				bean = new DataListBean();
				request.getSession().setAttribute("results", bean);
			}

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			DataListBean.DataBean result = new DataListBean.DataBean(x, y, r, flagIsHit, executionTime, LocalDateTime.now().format(formatter));

			bean.add(result);

			// Преобразование объекта DataListBean в JSON
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResult = objectMapper.writeValueAsString(bean);

			// Отправка JSON клиенту
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(jsonResult);
			out.flush();


		} catch (NumberFormatException e) {
			System.err.println(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}