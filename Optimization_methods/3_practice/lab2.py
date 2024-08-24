import math

# метод половинного деления
def bisection_method(f, a, b, e):
    k = 0
    while (b - a) / 2 > e:
        x1 = (a + b - e) / 2
        x2 = (a + b + e) / 2
        
        y1 = f(x1)        
        y2 = f(x2)
        
        if y1 > y2:
            a = x1
        else:
            b = x2
        xm = (a + b) / 2
        ym = f(xm)
    return [xm, ym]



# метод золотого сечения
def golden_section_search(f, a, b, e):
    x1 = a + 0.382 * (b - a)
    x2 = a + 0.618 * (b - a)

    while abs(b - a) > e:
        if f(x1) < f(x2):
            b = x2
            x2 = x1
            x1 = a + 0.382 * (b - a)
        else:
            a = x1
            x1 = x2
            x2 = a + 0.618 * (b - a)
    return [(b + a) / 2, f((b + a) / 2)]


# метод хорд
def secant_method(f, df, a, b, e):
    while True:
        # Вычисляем следующее приближение по формуле из методички
        x = a - (df(a) / (df(a) - df(b))) * (a - b)
        
        # Проверка на окончание поиска
        if abs(df(x)) <= e:
            return [x, f(x)]
        
        # Переход к новому отрезку
        if df(x) > 0:
            b = x
        else:
            a = x

# метод Ньютона
def newton_method(f, df, ddf, x0, e, max_iter=100):
    for i in range(max_iter):

        df_n = df(x0)
        ddf_n = ddf(x0)

        if abs(df_n) <= e:
            return [x0, f(x0)]

        x1 = x0 - df_n / ddf_n
        x0 = x1

    raise RuntimeError(f"Превышено максимальное число итераций ({max_iter})")



def f(x):
    return (x*x*x*x)/4 + (x*x) - 8*x + 12

def df(x):
    return x*x*x + 2*x - 8

def ddf(x):
    return 3*(x*x) + 2

a = 0
b = 2
x0 = 1
e = 0.05

print("Метод половинного деления")
result1 = bisection_method(f, a, b, e)
print(f"Приближенное точка и значение минимума: {result1}", "\n")

print("Метод золотого сечения")
result2 = golden_section_search(f, a, b, e)
print(f"Приближенное точка и значение минимума: {result2}", "\n")

print("Метод хорд")
result3 = secant_method(f, df, a, b, e)
print(f"Приближенное точка и значение минимума: {result3}", "\n")

print("Метод Ньютона")
result4 = newton_method(f, df, ddf, x0, e)
print(f"Приближенное точка и значение минимума: {result4}", "\n")
