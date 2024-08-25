import numpy as np
from scipy.optimize import minimize_scalar

def format_number(value):
	str_value = f"{value:.5f}"
	str_value = str_value.rstrip('0').rstrip('.') if '.' in str_value else str_value
	return str_value

# Градиенты функции
def grad_f(x, y):
	df_dx = 2*x - 4
	df_dy = 6 - 2*y
	return np.array([df_dx, df_dy])

# Метод градиентного спуска
def gradient_descent(x0, y0, alpha, eps, max_iter):
	x, y = x0, y0
	coords = [(x, y)]
	report = []

	report.append("## Метод градиентного спуска")

	report.append("Найдем градиент функции:")
	report.append("$$\\dfrac{df}{dx} = 2x - 4; \\quad \\dfrac{df}{dy}= 6 - 2y$$")
	report.append("$$\\nabla f(X) = ((2x - 4); (6 - 2y))$$")
	

	report.append("Возьмем в качестве первого приближения $X^{(0)} = (0; 0)$, т. е. $x^{(0)} = y^{(0)} = 0$ \
Тогда значение функции $f(X^{(0)}) = 0$, а вектор-строка градиента функции равен $\\nabla f(X^{(0)}) = (-4; 6)$. Выберем шаг итерации $\\lambda=0,25$ и рассчитаем параметры следующей точки:")

	for i in range(max_iter):
		grad = grad_f(x, y)
		norm_grad = np.linalg.norm(grad)

		if norm_grad < eps:
			report.append(f"**Алгоритм завершен на итерации {i + 1}, достигнута точность** $\\epsilon = {eps}$")
			break

		x_new = x - alpha * grad[0]
		y_new = y - alpha * grad[1]

		report.append(f"Рассчитаем параметры {i+1} точки:")
		report.append(f"$$x^{{({i+1})}} = x^{{({i})}} + \\lambda \\cdot \\nabla f (x^{{({i+1})}})) = {format_number(x)} + {format_number(alpha)} \\cdot {format_number(grad[0])} = {format_number(x_new)}$$")
		report.append(f"$$y^{{({i+1})}} = y^{{({i})}} + \\lambda \\cdot \\nabla f (y^{{({i+1})}})) = {format_number(y)} + {format_number(alpha)} \\cdot {format_number(grad[1])} = {format_number(y_new)}$$")

		report.append("Вычислим значение функции цели в новой точке и определим степень приближения:")
		report.append(f"$$f(X^{{({i+1})}}) = {format_number(x_new)}^2 - {format_number(y_new)}^2 - 4 \\cdot {format_number(x_new)} + 6 \\cdot {format_number(y_new)} = {format_number(x_new**2)} - {format_number(y_new**2)} - {format_number(4 * x_new)} + {format_number(6 * y_new)} = {format_number(f(x_new, y_new))}$$")
		if abs(f(x_new, y_new) - f(x, y)) < eps:
			report.append(f"**Точность достигнута:** $|f(X^{i+1} - f(X^{i})| = |{format_number(f(x_new, y_new))} - {format_number(f(x, y))}| = {format_number(abs(f(x_new, y_new) - f(x, y)))} < \\epsilon ({eps})$$")
		else:
			report.append(f"$$|f(X^{{({i+1})}}) - f(X^{{({i})}})| = |{format_number(f(x_new, y_new))} - {format_number(f(x, y))}| = {format_number(abs(f(x_new, y_new) - f(x, y)))} > \\epsilon ({eps})$$")
			report.append(f"Так как заданная точность не достигнута, продолжим итерационный процесс. Градиент функции в новой точке будет определяться вектором-строкой $\\nabla f(X^{{({i+1})}}) = ({format_number(x_new)};{format_number(y_new)})$.")
		report.append("\n")
		x, y = x_new, y_new
		coords.append((x, y))

	report.append(f"В пределах заданной точности, минимум найден в точке $(x^*, y^*) = ({format_number(x)}, {format_number(y)})$")
	report.append(f"$f_{{min}} = {format_number(f(x, y))}$")

	return coords, report

# Метод наискорейшего спуска
def steepest_descent(x0, y0, tol, max_iter):
    """Метод наискорейшего спуска с подробным выводом отчета в стиле примера."""
    x, y = x0, y0
    report = f"# Пример\n\n"
    report += f"Целевая функция: $f(x_1, x_2) = 5x_1^2 + 4x_1x_2 + x_2^2 - 16x_1 - 12x_2$\n\n"
    report += f"Начальная точка: $M_0 = ({x0}, {y0})$\n\n"

    for k in range(max_iter):
        grad = grad_f(x, y)
        norm_grad = np.linalg.norm(grad)

        # Форматирование вывода градиента
        report += f"$$\\frac{{df}}{{dx_1}}(M_{k}) = {grad[0]:.5f}$$\n"
        report += f"$$\\frac{{df}}{{dx_2}}(M_{k}) = {grad[1]:.5f}$$\n\n"

        if norm_grad < tol:
            report += "\n**Оптимум достигнут, норма градиента меньше допуска**.\n"
            break
        
        s = -grad

        # Функция одномерной оптимизации для нахождения шага λ
        def f_lambda(lambda_k):
            return f(x + lambda_k * s[0], y + lambda_k * s[1])

        # Нахождение оптимального шага λ
        result = minimize_scalar(f_lambda)
        lambda_k = result.x

        # Обновление значений x и y
        new_x = x + lambda_k * s[0]
        new_y = y + lambda_k * s[1]

        # Форматирование вывода шага и новой точки
        report += f"Подставляем значения в целевую функцию и находим оптимальный шаг $h_{{k+1}} = {lambda_k:.5f}$:\n"
        report += f"$$x_{{k+1}} = x_k - h_{{k+1}} \\cdot \\frac{{df}}{{dx_1}}(M_k) = {new_x:.5f}$$\n"
        report += f"$$y_{{k+1}} = y_k - h_{{k+1}} \\cdot \\frac{{df}}{{dx_2}}(M_k) = {new_y:.5f}$$\n\n"

        x, y = new_x, new_y

        report += f"Переходим к следующей итерации, считая начальной точкой $M_{k+1} = ({x:.5f}, {y:.5f})$\n\n"

    report += f"Тогда оптимальная точка $M^* = ({x:.5f}, {y:.5f})$\n"
    report += f"Значение функции в оптимальной точке: $f(M^*) = {f(x, y):.5f}$\n"

    # Вывод отчета
    print(report)
    return x, y



def f(x, y):
	return x**2 - y**2 - 4*x + 6*y

x0, y0 = 0.0, 0.0
alpha = 0.001
eps = 1e-6
max_iter = 3

# Метод градиентного спуска
# coords, report_gd = gradient_descent(x0, y0, alpha, eps, max_iter)
# x_min, y_min = coords[-1]

# # Печать отчета в формате Markdown для метода градиентного спуска
# print("\n".join(report_gd))
# print(f"\n**Итог: Минимум найден в точке x = {format_number(x_min)}, y = {format_number(y_min)}, f(x,y) = {format_number(f(x_min, y_min))}**\n")

# Метод наискорейшего спуска
x_min_sd, y_min_sd = steepest_descent(x0, y0, eps, max_iter)

# Печать отчета в формате Markdown для метода наискорейшего спуска
print(f"\n**Итог: Минимум найден в точке x = {x_min_sd}, y = {y_min_sd}, f(x,y) = {f(x_min_sd, y_min_sd)}**\n")
