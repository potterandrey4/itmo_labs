import math

# метод половинного деления
def bisection_method(f, a, b, e, max_iter=5):
	k = 1
	while (b - a) / 2 > e:
		print(f"- {k} итерация")
		print("	- Берём две точки вблизи интервала [a, b]:")
		x1 = (a + b - e) / 2
		print(f"		- $x_1 =  ({a:.5f}+{b:.5f}-{e}) / 2 = {x1:.5f}$")
		x2 = (a + b + e) / 2
		print(f"		- $x_2 =  ({a:.5f}+{b:.5f}+{e}) / 2 = {x2:.5f}$")
		print("	- Вычислим значения функций в этих точках")
		
		y1 = f(x1)
		print(f"		- $y_1 = {y1:.5f}$")
		
		y2 = f(x2)
		print(f"		- $y_2 = {y2:.5f}$")
		
		if y1 > y2:
			print("	- $y_1 > y_2$, следовательно:")
			print(f"		- $a = {x1:.5f}$")
			a = x1
			print(f"		- $[a,b] = [{a:.5f}, {b:.5f}]$")
		else:
			print("	- $y_2 > y_1$, следовательно:")
			print(f"		- $b = {x2:.5f}$")
			b = x2
			print(f"		- $[a,b] = [{a:.5f}, {b:.5f}]$")
		print()
		xm = (a + b) / 2
		ym = f(xm)
		
		k+=1
		# Если достигнуто максимальное количество итераций
		if k > max_iter:
			print(f"> требуемая точность вручную не достигнута")
			break

	print("#### $x = \\dfrac{a + b}{2}$\n#### $f(x) = f(x)$")
	return [xm, ym]



# метод золотого сечения
def golden_section_search(f, a, b, e, max_iter=5):
	k = 1
	x1 = a + 0.382 * (b - a)
	x2 = a + 0.618 * (b - a)
	print("- 1 итерация")
	print("	- Вычислим точки по формулам $x_1 = a+0.382(b-a)$, $x_2 = a+0.618(b-a)$:")
	print(f"		- $x_1 = {a:.5f}+0.382({b:.5f}-{a:.5f})= {x1:.5f}$")
	print(f"		- $x_2 = {a:.5f}+0.618({b:.5f}-{a:.5f})= {x2:.5f}$")
	k = 1
	while abs(b - a) > e:
		print(f"- {k} итерация")
		if f(x1) < f(x2):
			print(f"	- $f({x1:.5f}) < f({x2:.5f})$ или ${f(x1):.5f} < {f(x2):.5f}$, следовательно:")
			b = x2
			print(f"		- отрезок $[a; x_2]$ или $[{a:.5f}; {x2:.5f}]$")
			x2 = x1
			print(f"		- $x2 = x1 = {x1:.5f}$")
			x1 = a + 0.382 * (b - a)
			print(f"		- $x1 = a + 0.382 * (b - a) = {a:.5f} + 0.382 *({b:.5f} - {a:.5f}) = {x1:.5f}$")
		else:
			print(f"	- $f({x1:.5f}) > f({x2:.5f})$ или ${f(x1):.5f} > {f(x2):.5f}$, следовательно:")
			a = x1
			print(f"		- отрезок $[x_1; b]$ или $[{x1:.5f}; {b:.5f}]$")
			x1 = x2
			print(f"		- $x1 = x2 = {x2:.5f}$")
			x2 = a + 0.618 * (b - a)
			print(f"		- $x2 = a + 0.618 * (b - a) = {a:.5f} + 0.618 *({b:.5f} - {a:.5f}) = {x2:.5f}$")

		k += 1
		# Если достигнуто максимальное количество итераций
		if k > max_iter:
			print(f"> требуемая точность вручную не достигнута")
			break

	print("#### $x = \\dfrac{b + a}{2}$\n#### $f(x) = f(\\dfrac{b + a}{2})$")
	return [(b + a) / 2, f((b + a) / 2)]


# метод хорд
def secant_method(f, df, a, b, e, max_iter=5):
	x_res = 0
	k = 1
	df_x = 100

	while abs(df_x) > e and k <= max_iter:
		# Вычисляем следующее приближение по формуле из методички
		x = a - (df(a) / (df(a) - df(b))) * (a - b)
		df_x = df(x)

		# Печать отчета для текущей итерации
		print(f"- {k} итерация")
		print("    - $\\widetilde{x} = a - \\dfrac{f'(a)}{f'(a)-f'(b)}(a-b)", end="")
		print(f" = {a:.5f} - {df(a):.5f} / ({df(a):.5f} - {df(b):.5f}) * ({a:.5f} - {b:.5f}) = {x:.5f}$")
		print("    - $f'(\\widetilde{x}) = ", end="")
		print(f"f'({x:.5f}) = {df_x:.5f}$")

		# Проверка и выбор нового отрезка
		if df_x > 0:
			print("    - $f'(\\widetilde{x}) > 0$, следовательно $b = x$")
			b = x
		else:
			print("    - $f'(\\widetilde{x}) <= 0$, следовательно $a = x$")
			a = x

		# Условие завершения итераций
		if abs(df_x) <= e:
			print("    - $|f'(\\widetilde{x})| <= e$", end=" ")
			print(f"или ${abs(df(x)):.5f} <= {e}$ $\\Rightarrow$ заканчиваем итерации")
			x_res = x
			break

		k += 1
		# Если достигнуто максимальное количество итераций
		if k > max_iter:
			print(f"> требуемая точность вручную не достигнута")
			break

	print("### $x^* = \\widetilde{x}$\n### $f^* = f(\\widetilde{x})$")
	return [x_res, f(x_res)]



# метод Ньютона
def newton_method(f, df, ddf, x0, e, max_iter=5):
    k = 0
    xk = x0

    while k < max_iter:
        # Вычисляем производные в текущей точке
        df_xk = df(xk)
        ddf_xk = ddf(xk)

        # Печать текущей итерации
        print(f"- {k + 1} итерация")
        print("    - $x_{k+1} = x_k - \\dfrac{f'(x_k)}{f''(x_k)}$", end="")
        print(f" = {xk:.5f} - {df_xk:.5f} / {ddf_xk:.5f} = ", end="")
        
        # Вычисляем новую точку
        xk1 = xk - df_xk / ddf_xk
        print(f"{xk1:.5f}$")

        # Проверка на окончание поиска
        if abs(df_xk) <= e:
            print(f"    - $|f'(x_k)| <= e$, или ${abs(df_xk):.5f} <= {e}$ $\\Rightarrow$ заканчиваем итерации")
            break
        
        # Переход к следующей итерации
        xk = xk1
        k += 1
    
    # Если достигнуто максимальное количество итераций
    if k == max_iter:
        print(f"> требуемая точность вручную не достигнута")

    print(f"### $x^* \\approx x_k = {xk:.5f}$\n### $f^* \\approx f(x_k) = {f(xk):.5f}$")
    return [xk, f(xk)]




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

# метод половинного деления
print("## Метод половинного деления")
result1 = bisection_method(f, a, b, e)
print(f"\n### Точка минимума {result1[0]:.5f} и приближенное значение {result1[1]:.5f}", "\n---\n")


# метод золотого сечения
print("## Метод золотого сечения")
result2 = golden_section_search(f, a, b, e)
print(f"\n### Точка минимума {result2[0]:.5f} и приближенное значение {result2[1]:.5f}", "\n---\n")


# метод хорд
print("## Метод хорд")
result3 = secant_method(f, df, a, b, e)
print(f"\n### Точка минимума {result3[0]:.5f} и приближенное значение {result3[1]:.5f}", "\n---\n")


# метод Ньютона
print("## Метод Ньютона")
result4 = newton_method(f, df, ddf, x0, e)
print(f"\n### Точка минимума {result4[0]:.5f} и приближенное значение {result4[1]:.5f}", "\n---\n")
