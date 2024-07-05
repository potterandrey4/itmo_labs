import numpy as np
import math

def find_extremum(f, a, b, tol, max_iter=100):
    # Инициализация точек
    x1, x3 = a, b
    x2 = (x1 + x3) / 2

    for _ in range(max_iter):
        # Вычисление значений функции в точках
        f1, f2, f3 = f(x1), f(x2), f(x3)

        # Коэффициенты квадратичной функции f(x) = A*x^2 + B*x + C
        A = (f3 - (x3 * (f2 - f1) + x2 * f1 - x1 * f2) / (x2 - x1)) / (x3 * (x3 - x1) * (x3 - x2))
        B = (f2 - f1) / (x2 - x1) - A * (x1 + x2)
        C = f1 - A * x1**2 - B * x1

        # Находим вершину параболы (точку экстремума)
        x_extremum = -B / (2 * A)
        
        # Если вершина находится вне текущего интервала, корректируем ее
        if x_extremum < a or x_extremum > b:
            x_extremum = (a + b) / 2
        
        # Проверка сходимости
        if np.abs(x_extremum - x2) < tol:
            return x_extremum, f(x_extremum)
        
        # Обновляем точки
        if x_extremum < x2:
            if f(x_extremum) < f2:
                x3, x2 = x2, x_extremum
            else:
                x1 = x_extremum
        else:
            if f(x_extremum) < f2:
                x1, x2 = x2, x_extremum
            else:
                x3 = x_extremum

    return x_extremum, f(x_extremum)

def f(x):
    return x*x / 2 - math.sin(x)

a = 0
b = 1
eps = 0.0001

extremum_x, extremum_y = find_extremum(f, a, b, eps)
print(f"Минимум функции f(x) на отрезке [{a}, {b}] достигается в точке x = {extremum_x:.5f}, где f(x) = {extremum_y:.5f}\n\n")

