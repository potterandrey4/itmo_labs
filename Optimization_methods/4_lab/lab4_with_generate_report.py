import numpy as np
from scipy.optimize import minimize_scalar

def format_number(value):
	str_value = f"{value:.5f}"
	str_value = str_value.rstrip('0').rstrip('.') if '.' in str_value else str_value
	return str_value

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
def steepest_descent(x0, y0, eps, max_iter):
    x, y = x0, y0
    coords = [(x, y)]
    report = []

    report.append("## Метод наискорейшего спуска")
    report.append(f"Целевая функция $f(x_1, x_2) = x_1^2 + x_2^2 + 1.5 x_1 x_2$, начальная точка $M_0 = ({x0},{y0})$")

    for k in range(max_iter):
        grad = grad_f(x, y)
        norm_grad = np.linalg.norm(grad)
        
        report.append(f"### Итерация {k + 1}")
        report.append(f"Вычислим градиент в точке $M_{k} = ({format_number(x)}, {format_number(y)})$")
        report.append(f"$$\\frac{{df}}{{dx_1}}(M_{k}) = 2x_1 + 1.5x_2 = {format_number(grad[0])}$$")
        report.append(f"$$\\frac{{df}}{{dx_2}}(M_{k}) = 2x_2 + 1.5x_1 = {format_number(grad[1])}$$")
        
        if norm_grad < eps:
            report.append(f"**Алгоритм завершен на итерации {k + 1}, достигнута точность** $\\epsilon = {eps}$")
            break

        s = -grad
        report.append(f"Направление спуска: $s = -\\nabla f(M_{k}) = ({format_number(s[0])}, {format_number(s[1])})$")

        # Функция одномерной оптимизации для нахождения шага λ
        def f_lambda(lambda_k):
            return f(x + lambda_k * s[0], y + lambda_k * s[1])

        # Нахождение оптимального шага λ
        result = minimize_scalar(f_lambda)
        lambda_k = result.x
        report.append(f"Оптимальный шаг $\\lambda_{k + 1}$ найден: $\\lambda_{k + 1} = {format_number(lambda_k)}$")

        # Обновление значений x и y
        x_new = x + lambda_k * s[0]
        y_new = y + lambda_k * s[1]
        report.append(f"Обновляем координаты: $x_{k + 1} = x_{k} + \\lambda_{k + 1} s_1 = {format_number(x_new)}$")
        report.append(f"$y_{k + 1} = y_{k} + \\lambda_{k + 1} s_2 = {format_number(y_new)}$")
        report.append(f"Новое значение функции: $f(x_{k + 1}, y_{k + 1}) = {format_number(f(x_new, y_new))}$")

        x, y = x_new, y_new
        coords.append((x, y))

    report.append(f"Минимум найден в точке $(x^*, y^*) = ({format_number(x)}, {format_number(y)})$")
    report.append(f"$f_{{min}} = {format_number(f(x, y))}$")

    return x, y, report



# <3 вариант>
# https://www.desmos.com/3d/ufu7c1xwht (экстремумов нет)

# def f(x, y):
# 	return x**2 - y**2 - 4*x + 6*y

# # Градиенты функции
# def grad_f(x, y):
# 	df_dx = 2*x - 4
# 	df_dy = 6 - 2*y
# 	return np.array([df_dx, df_dy])

# </3 вариант>


# вымышленный вариант: z = x^2 - xy + y^2 - 7x + 8y
def f(x, y):
    return x**2 - x*y + y**2 - 7*x + 8*y

def grad_f(x, y):
    df_dx = 2*x - y - 7
    df_dy = -x + 2*y + 8
    return np.array([df_dx, df_dy])

x0, y0 = 1.0, 2.0
alpha = 0.001
eps = 1e-6
max_iter = 3

# Метод градиентного спуска
coords, report_gd = gradient_descent(x0, y0, alpha, eps, max_iter)
x_min, y_min = coords[-1]

# Печать отчета в формате Markdown для метода градиентного спуска
print("\n".join(report_gd))
print(f"\n**Итог: Минимум найден в точке x = {format_number(x_min)}, y = {format_number(y_min)}, f(x,y) = {format_number(f(x_min, y_min))}**\n")

# Метод наискорейшего спуска
x_min_sd, y_min_sd, report = steepest_descent(x0, y0, eps, max_iter)

# Печать отчета в формате Markdown для метода наискорейшего спуска
print("\n".join(report))
print(f"\n**Итог: Минимум найден в точке x = {x_min_sd}, y = {y_min_sd}, f(x,y) = {f(x_min_sd, y_min_sd)}**\n")
