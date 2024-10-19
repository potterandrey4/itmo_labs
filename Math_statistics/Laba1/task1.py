from scipy.stats import norm, gamma, expon
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# Параметры нормального распределения
mu, sigma = 0, 1
n = 10000

# Генерация одной выборки
samples = norm.rvs(loc=mu, scale=sigma, size=n)

# Выборочное среднее
mean = np.mean(samples)

# Генерация нескольких выборок для выборочного среднего
means = [np.mean(norm.rvs(loc=mu, scale=sigma, size=n)) for _ in range(1000)]

# Преобразуем список means в массив NumPy
means = np.array(means)

# Нормировка и стандартизация выборочного среднего
normalized_means = (means - mu) / (sigma / np.sqrt(n))

# Гистограмма и график плотности для выборочного среднего
sns.histplot(normalized_means, kde=True, stat="density")
plt.title("Гистограмма нормализованного выборочного среднего")
plt.show()

# Генерация выборок для выборочной дисперсии
variances = [np.var(norm.rvs(loc=mu, scale=sigma, size=n), ddof=1) for _ in range(1000)]

# Нормировка выборочной дисперсии
normalized_variances = (variances - np.var(samples)) / np.sqrt(2/n)

# Гистограмма и график плотности для выборочной дисперсии
sns.histplot(normalized_variances, kde=True, stat="density")
plt.title("Гистограмма нормализованной выборочной дисперсии")
plt.show()

# Генерация выборок для выборочной медианы
medians = [np.median(norm.rvs(loc=mu, scale=sigma, size=n)) for _ in range(1000)]

# Гистограмма для выборочной медианы
sns.histplot(medians, kde=True, stat="density")
plt.title("Гистограмма выборочной медианы")
plt.show()

# Вывод характеристик для выборочного среднего
print(f"Среднее выборочного среднего: {np.mean(normalized_means)}")
print(f"Дисперсия выборочного среднего: {np.var(normalized_means)}")
print(f"Медиана выборочного среднего: {np.median(normalized_means)}\n")

# Вывод характеристик для выборочной дисперсии
print(f"Среднее выборочной дисперсии: {np.mean(normalized_variances)}")
print(f"Дисперсия выборочной дисперсии: {np.var(normalized_variances)}")
print(f"Медиана выборочной дисперсии: {np.median(normalized_variances)}\n")

# Вывод характеристик для выборочной медианы
print(f"Среднее выборочной медианы: {np.mean(medians)}")
print(f"Дисперсия выборочной медианы: {np.var(medians)}")
print(f"Медиана выборочной медианы: {np.median(medians)}")

# Реализация проверки сходимости nF(X(2)) -> U1 ~ Gamma(2, 1)

# Генерация второй порядковой статистики (2-я по величине выборочная статистика)
second_order_statistics = [sorted(norm.rvs(loc=mu, scale=sigma, size=n))[1] for _ in range(1000)]

# Считаем U1 как nF(X(2))
u1 = n * np.array([norm.cdf(x) for x in second_order_statistics])

# Построение гистограммы U1 и наложение графика плотности Gamma(2,1)
sns.histplot(u1, kde=True, stat="density")
x = np.linspace(0, 5, 100)
plt.plot(x, gamma.pdf(x, a=2, scale=1), label='Gamma(2,1)', color='red')
plt.legend()
plt.title("Гистограмма для U1 с наложенной Gamma(2,1)")
plt.show()

# Реализация проверки сходимости n(1 - F(X(n))) -> U2 ~ Exp(1)

# Генерация последней порядковой статистики (максимального значения выборки)
last_order_statistics = [sorted(norm.rvs(loc=mu, scale=sigma, size=n))[-1] for _ in range(1000)]

# Считаем U2 как n(1 - F(X(n)))
u2 = n * (1 - np.array([norm.cdf(x) for x in last_order_statistics]))

# Построение гистограммы U2 и наложение графика плотности Exp(1)
sns.histplot(u2, kde=True, stat="density")
plt.plot(x, expon.pdf(x, scale=1), label='Exp(1)', color='green')
plt.legend()
plt.title("Гистограмма для U2 с наложенной Exp(1)")
plt.show()
