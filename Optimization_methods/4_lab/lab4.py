import numpy as np
from scipy.optimize import minimize_scalar

# Метод градиентного спуска
def gradient_descent(x0, y0, alpha, eps, max_iter):
    x, y = x0, y0
    coords = [(x, y)]
    for _ in range(max_iter):
        grad = grad_f(x, y)
        if np.linalg.norm(grad) < eps:
            break
        # Использование антиградиента
        x = x - alpha * grad[0]
        y = y - alpha * grad[1]
        coords.append((x, y))
    return coords


# Метод наискорейшего спуска
def steepest_descent(x0, y0, eps, max_iter):
    x, y = x0, y0
    for k in range(max_iter):
        grad = grad_f(x, y)
        norm_grad = np.linalg.norm(grad)
        if norm_grad < eps:
            break
        s = -grad

        # Функция одномерной оптимизации для нахождения шага λ
        def f_lambda(lambda_k):
            return f(x + lambda_k * s[0], y + lambda_k * s[1])

        # Нахождение оптимального шага λ
        result = minimize_scalar(f_lambda)
        lambda_k = result.x

        # Обновление значений x и y
        x = x + lambda_k * s[0]
        y = y + lambda_k * s[1]
    return x, y



# <3 вариант>
# https://www.desmos.com/3d/ufu7c1xwht (экстремумов нет)

# def f(x, y):
#   return x**2 - y**2 - 4*x + 6*y

# # Градиенты функции
# def grad_f(x, y):
#   df_dx = 2*x - 4
#   df_dy = 6 - 2*y
#   return np.array([df_dx, df_dy])

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
max_iter = 1000

print("Метод градиентного спуска:")
coords = gradient_descent(x0, y0, alpha, eps, max_iter)

x_min, y_min = coords[-1]
print(f"Minimum at x = {x_min}, y = {y_min}, f(x,y) = {f(x_min, y_min)}")

print("\nМетод наискорейшего спуска:")
x_min, y_min = steepest_descent(x0, y0, eps, max_iter)
print(f"Minimum at x = {x_min}, y = {y_min}, f(x,y) = {f(x_min, y_min)}")

# градиент:                 https://www.desmos.com/calculator/vslfmeypxs
# наибыстрейший спуск:      https://www.desmos.com/calculator/rfslsqwvoo

# 4 вариант для примера
# https://www.wolframalpha.com/input?i=extremum+f%28x%2C+y%29+%3D+5x%5E2+%2B+4xy+%2B+y%5E2+-+16x+-12y