import math

# метод половинного деления
def bisection_method(f, a, b, e):
	k = 0
	while (b - a) / 2 > e:
		k+=1
		print(f"- {k} итерация")
		print("	- Берём две точки вблизи интервала [a, b]:")
		x1 = (a + b - e) / 2
		print(f"		- $x_1 =  ({a:.5f}+{b:.5f}-0.03) / 2 = {x1:.5f}$")
		x2 = (a + b + e) / 2
		print(f"		- $x_2 =  ({a:.5f}+{b:.5f}+0.03) / 2 = {x2:.5f}$")
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
	print("#### $x = \\dfrac{a + b}{2}$\n#### $f(x) = f(x)$")
	return [xm, ym]



# метод золотого сечения
def golden_section_search(f, a, b, e):
	x1 = a + 0.382 * (b - a)
	x2 = a + 0.618 * (b - a)
	print("- 1 итерация")
	print("	- Вычислим точки по формулам $x_1 = a+0.382(b-a)$, $x_2 = a+0.618(b-a)$:")
	print(f"		- $x_1 = {a:.5f}+0.382({b:.5f}-{a:.5f})= {x1:.5f}$")
	print(f"		- $x_2 = {a:.5f}+0.618({b:.5f}-{a:.5f})= {x2:.5f}$")
	k = 1
	while abs(b - a) > e:
		k+=1
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
	print("#### $x = \\dfrac{b + a}{2}$\n#### $f(x) = f(\\dfrac{b + a}{2})$")
	return [(b + a) / 2, f((b + a) / 2)]


# метод хорд
def secant_method(f, df, a, b, e):
	x_res = 0
	f_xres = 0
	k = 1;

	df_x = 100


	while abs(df_x) > e:
		x = a - ( (df(a)) / (df(a))-df(b) ) * (a-b)
		df_x = df(x)

		print(f"- {k} итерация")
		print("	- $\\widetilde{x} = a - \\dfrac{f'(a)}{f'(a)-f'(b)}(a-b)", end="")
		print(f" = {a:.5f} - {df(a):.5f} / ({df(a):.5f}-{df(b):.5f}) * ({a:.5f}-{b:.5f}) = {x:.5f}$")
		print("	- $f'(\\widetilde{x}) = ", end="")
		print(f"f'({x:.5f}) = {df_x:.5f}$")

		if df_x > 0:
			print("	- $f'(\\widetilde{x}) > 0$, следовательно $b = x$")
			b = x
		else:
			print("	- $f'(\\widetilde{x}) <= 0$, следовательно $a = x$")
			a = x
		if abs(df_x) <= e:
			print("	- $|f'(\\widetilde{x})| <= e$", end=" ")
			print(f"или ${abs(df(x)):.5f} <= {e}$ $\\Rightarrow$ заканчиваем итерации")
			x_res = x
			f_xes = f(x_res)

		k+=1

	print("### $x^* = \\widetilde{x}$\n### $f^* = f(\\widetilde{x})$")
	return [x_res, f(x_res)]


# метод Ньютона
def newton_method(f, df, ddf, x0, e, max_iter=100):
	k = 1
	for i in range(max_iter):
		print(f"- {k} итерация")
		print(f"	- $x_{k} = {x0:.5f}$")

		df_n = df(x0)
		print(f"	- $f'(x_{k}) = {df_n:.5f}$")
		ddf_n = ddf(x0)
		print(f"	- $f''(x_{k}) = {ddf_n:.5f}$")

		if abs(df_n) <= e:
			print("	- $|f'({x})| <= e$", end=" ")
			print(f"или ${abs(df_n):.5f} <= {e}$ $\\Rightarrow$ заканчиваем итерации")
			return [x0, f(x0)]

		x1 = x0 - df_n / ddf_n
		print(f"	- $x_{k+1} = {x1:.5f}$")
		x0 = x1

		k+=1

	raise RuntimeError(f"Превышено максимальное число итераций ({max_iter})")



def f(x):
	return (x**2)/2 - math.sin(x)

def df(x):
	return x - math.cos(x)

def ddf(x):
	return 1 + math.sin(x)

a = 0
b = 1
x0 = 1
e = 0.03

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


# Метод половинного деления
# Приближенное точка и значение минимума: [0.734921875, -0.4004741168871258] 

# Метод золотого сечения
# Приближенное точка и значение минимума: [0.7401196344312161, -0.40048771643329223] 

# Метод хорд
# Приближенное точка и значение минимума: [0.7568213654900753, -0.4002246902706019] 

# Метод Ньютона
# Приближенное точка и значение минимума: [0.7503638678402439, -0.4003819858614023] 