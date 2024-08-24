import numpy as np
import math

def find_extremum(f, x1, delta_x, eps):
    k = 0
    while True:
        k+=1
        # Шаг 2: Вычисление x2
        x2 = x1 + delta_x
        
        # Шаг 3: Вычисление значений функции в точках x1 и x2
        f_x1 = f(x1)
        f_x2 = f(x2)
        
        # Шаг 4: Сравнение значений f(x1) и f(x2)
        if f_x1 > f_x2:
            x3 = x1 + 2 * delta_x
        else:
            x3 = x1 - delta_x
        
        # Шаг 5: Вычисление значения функции в точке x3
        f_x3 = f(x3)
        
        # Шаг 6: Находим Fmin и Xmin
        Fmin = min(f_x1, f_x2, f_x3)
        Xmin = [x1, x2, x3][[f_x1, f_x2, f_x3].index(Fmin)]
        
        # Шаг 7: Вычисление точки минимума полинома
        numerator = ((x2**2 - x3**2) * f_x1 + (x3**2 - x1**2) * f_x2 + (x1**2 - x2**2) * f_x3)
        denominator = 2 * ((x2 - x3) * f_x1 + (x3 - x1) * f_x2 + (x1 - x2) * f_x3)
        
        # Проверка на случай, если знаменатель равен нулю
        if denominator == 0:
            x = Xmin
        else:
            x = numerator / denominator
        
        f_x = f(x)
        
        # Шаг 8: Проверка выполнения условий окончания расчета
        if abs((Fmin - f_x) / f_x) < eps and abs((Xmin - x) / x) < eps:
            return x, f_x, k
        
        # Обновление точек для следующей итерации
        if x1 < x < x3:
            x1, x2, x3 = sorted([x1, x2, x3, x])[:3]
        else:
            x1 = x


def f(x):
    return (x*x*x*x)/4 + (x*x) - 8*x + 12

x = 1
delta_x = 0.3
eps = 0.0001

extremum_x, extremum_y, k = find_extremum(f, x, delta_x, eps)
print(f"Минимум функции f(x) на отрезке [0, 2] достигается в точке x = {extremum_x:.5f}, где f(x) = {extremum_y:.5f} за {k} итераций")

