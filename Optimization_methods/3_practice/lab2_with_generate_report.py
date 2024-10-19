import math

def format_number(value):
	str_value = f"{value:.5f}"
	str_value = str_value.rstrip('0').rstrip('.') if '.' in str_value else str_value
	return str_value

# метод половинного деления
def bisection_method(f, a, b, e, max_iter=5):
	k = 1
	while (b - a) / 2 > e:
		print(f"- {k} итерация")
		print("	- Берём две точки вблизи интервала [a, b]:")
		x1 = (a + b - e) / 2
		print(f"		- $x_1 =  \\frac{{{format_number(a)}+{format_number(b)}-{e}}}{{2}} = {format_number(x1)}$")
		x2 = (a + b + e) / 2
		print(f"		- $x_2 =  \\frac{{{format_number(a)}+{format_number(b)}+{e}}}{{2}} = {format_number(x2)}$")
		print("	- Вычислим значения функций в этих точках")
		
		y1 = f(x1)
		print(f"		- $y_1 = {format_number(y1)}$")
		
		y2 = f(x2)
		print(f"		- $y_2 = {format_number(y2)}$")
		
		if y1 > y2:
			print("	- $y_1 > y_2$, следовательно:")
			print(f"		- $a = {format_number(x1)}$")
			a = x1
			print(f"		- $[a,b] = [{format_number(a)}, {format_number(b)}]$")
		else:
			print("	- $y_2 > y_1$, следовательно:")
			print(f"		- $b = {format_number(x2)}$")
			b = x2
			print(f"		- $[a,b] = [{format_number(a)}, {format_number(b)}]$")
		print()
		xm = (a + b) / 2
		ym = f(xm)
		
		k+=1
		# Если достигнуто максимальное количество итераций
		if k > max_iter:
			print(f"> требуемая точность вручную не достигнута")
			break

	print("#### $x = \\frac{a + b}{2}$\n#### $f(x) = f(x)$")
	return [xm, ym]



# метод золотого сечения
def golden_section_search(f, a, b, e, max_iter=5):
	k = 1
	x1 = a + 0.382 * (b - a)
	x2 = a + 0.618 * (b - a)
	print("- 1 итерация")
	print("	- Вычислим точки по формулам $x_1 = a+0.382(b-a)$, $x_2 = a+0.618(b-a)$:")
	print(f"		- $x_1 = {format_number(a)}+0.382({format_number(b)}-{format_number(a)})= {format_number(x1)}$")
	print(f"		- $x_2 = {format_number(a)}+0.618({format_number(b)}-{format_number(a)})= {format_number(x2)}$")
	k = 1
	while abs(b - a) > e:
		print(f"- {k} итерация")
		if f(x1) < f(x2):
			print(f"	- $f({format_number(x1)}) < f({format_number(x2)})$ или ${format_number(f(x1))} < {format_number(f(x2))}$, следовательно:")
			b = x2
			print(f"		- отрезок $[a; x_2]$ или $[{format_number(a)}; {format_number(x2)}]$")
			x2 = x1
			print(f"		- $x_2 = x_1 = {format_number(x1)}$")
			x1 = a + 0.382 * (b - a)
			print(f"		- $x_1 = a + 0.382 * (b - a) = {format_number(a)} + 0.382 *({format_number(b)} - {format_number(a)}) = {format_number(x1)}$")
		else:
			print(f"	- $f({format_number(x1)}) > f({format_number(x2)})$ или ${format_number(f(x1))} > {format_number(f(x2))}$, следовательно:")
			a = x1
			print(f"		- отрезок $[x_1; b]$ или $[{format_number(x1)}; {format_number(b)}]$")
			x1 = x2
			print(f"		- $x1 = x2 = {format_number(x2)}$")
			x2 = a + 0.618 * (b - a)
			print(f"		- $x2 = a + 0.618 * (b - a) = {format_number(a)} + 0.618 *({format_number(b)} - {format_number(a)}) = {format_number(x2)}$")

		k += 1
		# Если достигнуто максимальное количество итераций
		if k > max_iter:
			print(f"> требуемая точность вручную не достигнута")
			break

	print("#### $x = \\frac{b + a}{2}$\n#### $f(x) = f(\\frac{b + a}{2})$")
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
		print("    - $\\widetilde{x} = a - \\frac{f'(a)}{f'(a)-f'(b)}(a-b)", end="")
		print(f" = {format_number(a)} - \\frac{{{format_number(df(a))}}}{{{format_number(df(a))} - {format_number(df(b))}}} * ({format_number(a)} - {format_number(b)}) = {format_number(x)}$")
		print("    - $f'(\\widetilde{x}) = ", end="")
		print(f"f'({format_number(x)}) = {format_number(df_x)}$")

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
        print("    - $x_{k+1} = x_k - \\frac{f'(x_k)}{f''(x_k)}", end="")
        print(f" = {format_number(xk)} - \\frac{{{format_number(df_xk)}}}{{{format_number(ddf_xk)}}} = ", end="")
        
        # Вычисляем новую точку
        xk1 = xk - df_xk / ddf_xk
        print(f"{format_number(xk1)}$")

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

    print(f"### $x^* \\approx x_k = {format_number(xk)}$\n### $f^* \\approx f(x_k) = {f(xk):.5f}$")
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
