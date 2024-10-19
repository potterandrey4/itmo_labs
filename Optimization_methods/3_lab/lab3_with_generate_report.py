def find_extremum(f, x1, delta_x, eps, max_iterations=3):
    k = 1
    # Шаг 2: Вычисление x2
    x2 = x1 + delta_x
    
    # Шаг 3: Вычисление значений функции в точках x1 и x2
    f_x1 = f(x1)
    f_x2 = f(x2)
    
    print(f"## Итерация {k}")
    print(f"**Шаг 2.** Вычисляем вторую точку: $x_2 = x_1 + \\Delta x = {x1:.5f} + {delta_x:.5f} = {x2:.5f}$")
    print(f"**Шаг 3.** Вычисляем значения функции в точках: $f(x_1) = {f_x1:.5f}$, $f(x_2) = {f_x2:.5f}$")
    
    # Шаг 4: Сравнение значений f(x1) и f(x2)
    if f_x1 > f_x2:
        x3 = x1 + 2 * delta_x
        print(f"**Шаг 4а.** Поскольку $f(x_1) > f(x_2)$, положим $x_3 = x_1 + 2\\Delta x = {x3:.5f}$")
    else:
        x3 = x1 - delta_x
        print(f"**Шаг 4б.** Поскольку $f(x_1) \\leq f(x_2)$, положим $x_3 = x_1 - \\Delta x = {x3:.5f}$")
    
    # Шаг 5: Вычисление значения функции в точке x3
    f_x3 = f(x3)
    print(f"**Шаг 5.** Вычисляем $f(x_3) = {f_x3:.5f}$")
    
    while k <= 3:
        print(f"## Итерация {k}")
        # Шаг 6: Находим Fmin и Xmin
        Fmin = min(f_x1, f_x2, f_x3)
        Xmin = [x1, x2, x3][[f_x1, f_x2, f_x3].index(Fmin)]
        print(f"**Шаг 6.** Минимальное значение $F_{{min}} = \\min\\{{f(x_1), f(x_2), f(x_3)\\}} = {Fmin:.5f}$ при $x_{{min}} = {Xmin:.5f}$")
        
        # Шаг 7: Вычисление точки минимума полинома
        numerator = ((x2**2 - x3**2) * f_x1 + (x3**2 - x1**2) * f_x2 + (x1**2 - x2**2) * f_x3)
        denominator = 2 * ((x2 - x3) * f_x1 + (x3 - x1) * f_x2 + (x1 - x2) * f_x3)
        
        # Проверка на случай, если знаменатель равен нулю
        if denominator == 0:
            x = Xmin
            print(f"**Шаг 7.** Знаменатель оказался равен нулю. Полагаем $x = x_{{min}} = {x:.5f}$")
        else:
            x = numerator / denominator
            print(f"**Шаг 7.** Вычисляем точку минимума квадратичного полинома:\n\n")
            # print(f"$$x = \\dfrac{{(x_2^2 - x_3^2)f(x_1) + (x_3^2 - x_1^2)f(x_2) + (x_1^2 - x_2^2)f(x_3)}}{{2(x_2 - x_3)f(x_1) + (x_3 - x_1)f(x_2) + (x_1 - x_2)f(x_3)}} = {x:.5f}$$\n\n")
            print(f"$$x = \\dfrac{{({x2**2:.5f} - {x3**2:.5f}){f_x1:.5f} + ({x3**2:.5f} - {x1**2:.5f}){f_x2:.5f} + ({x1**2:.5f} - {x2**2:.5f}){f_x3:.5f}}}{{2({x2:.5f} - {x3:.5f}){f_x1:.5f} + ({x3:.5f} - {x1:.5f}){f_x2:.5f} + ({x1:.5f} - {x2:.5f}){f_x3:.5f}}} = {x:.5f}$$\n\n")
        
        f_x = f(x)
        print(f"**Шаг 7.** Вычисляем значение функции в найденной точке $f(x) = {f_x:.5f}$")
        
        # Шаг 8: Проверка выполнения условий окончания расчета
        condition1 = abs((Fmin - f_x) / f_x) < eps
        condition2 = abs((Xmin - x) / x) < eps
        if condition1 and condition2:
            print(f"**Шаг 8а.** Условия окончания расчета выполнены:\n\n")
            print(f"$$\\left|\\dfrac{{F_{{min}} - f(x)}}{{f(x)}}\\right| < \\epsilon \\quad и \\quad \\left|\\dfrac{{x_{{min}} - x}}{{x}}\\right| < \\epsilon$$\n\n")
            print(f"Завершаем итерации: $x^* = {x:.5f}$\n\n")
            return x, f_x
        
        # Обновление точек для следующей итерации
        if x1 < x < x3:
            x1, x2, x3 = sorted([x1, x2, x3, x])[:3]
            print(f"**Шаг 8б.** Условия окончания расчета не выполнены и $\\overline{{\\rm x}} \\in [x_1, x_3]$:\n\n")
            print("Выберем точку, в которой функция принимает минимальное значение и две точки рядом от неё")
            if x_answ < x2:
                if f(x_answ) < f2:
                    x3, x2 = x2, x
                else:
                    x1 = x
            else:
                if f(x_answ) < f2:
                    x1, x2 = x2, x
                else:
                    x3 = x

        else:
            x1 = x
        
        k += 1
    
    return x, f_x




def f(x):
    return (x*x*x*x)/4 + (x*x) - 8*x + 12

x = 1
delta_x = 0.3
eps = 0.0001

extremum_x, extremum_y = find_extremum(f, x, delta_x, eps)
print(f"Минимум функции f(x) на отрезке [0, 2] достигается в точке x = {extremum_x:.5f}, где f(x) = {extremum_y:.5f}\n\n")

