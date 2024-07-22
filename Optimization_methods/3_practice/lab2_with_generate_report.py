import math

# метод половинного деления
def bisection_method(f, a, b, e):
	k = 0
	while (b - a) / 2 > e:
		k+=1
		print(f"- {k} итерация")
		print("	- Берём две точки вблизи интервала [a, b]:")
		x1 = (a + b - e) / 2
		print(f"			- $x_1 =  ({a:.5f}+{b:.5f}-0.03) / 2 = {x1:.5f}$")
		x2 = (a + b + e) / 2
		print(f"			- $x_2 =  ({a:.5f}+{b:.5f}-0.03) / 2 = {x2:.5f}$")
		print("	- Вычислим значения функций в этих точках")
		
		y1 = f(x1)
		print(f"			- $y_1 = {y1:.5f}$")
		
		y2 = f(x2)
		print(f"			- $y_2 = {y2:.5f}$")
		
		if y1 > y2:
			print("	- $y_1 > y_2$, следовательно:")
			print(f"		- $a = {x1:.5f}$")
			print(f"		- $[a,b] = [{a:.5f}, {b:.5f}]$")
			a = x1
		else:
			print("	- $y_2 > y_1$, следовательно:")
			print(f"		- $b = {x2:.5f}$")
			print(f"		- $[a,b] = [{a:.5f}, {b:.5f}]$")
			b = x2
		print()
		xm = (a + b) / 2
		ym = f(xm)
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
		# if (k == 5):
		# 	print(f"{k}: (b + a) / 2, f((b + a) / 2) = {((b + a) / 2):.5f}; {(f((b + a) / 2)):.5f}")
	return [(b + a) / 2, f((b + a) / 2)]


# метод хорд
def secant_method(f, df, a, b, e):
	x_res = 0
	f_xres = 0

	x = a - ( (df(a)) / (df(a))-df(b) ) * (a-b)
	f_x = df(x)

	while abs(f_x) > e:
		x = a - ( (df(a)) / (df(a))-df(b) ) * (a-b)
		f_x = df(x)

		if f_x > 0:
			b = x
		else:
			a = x
		if abs(f_x) <= e:
			x_res = x
			f_xes = f_x

	return [x_res, f(x_res)]


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
	return (x**2)/2 - math.sin(x)

def df(x):
	return x - math.cos(x)

def ddf(x):
	return 1 + math.sin(x)


# метод половинного деления
print("метод половинного деления")
result1 = bisection_method(f, 0, 1, 0.03)
print(f"\nТочка минимума и приближенное значение: {result1}", "\n", "---")


# метод золотого сечения
result2 = golden_section_search(f, 0, 1, 0.03)
print("метод золотого сечения")
print(f"Приближенное точка и значение минимума: {result2}", "\n")


# метод хорд
print("метод хорд")
result3 = secant_method(f, df, 0, 1, 0.03)
print(f"Приближенное точка и значение минимума: {result3}", "\n")


# метод Ньютона
result4 = newton_method(f, df, ddf, 1, 0.03)
print("метод Ньютона")
print(f"Приближенное точка и значение минимума: {result4}", "\n")