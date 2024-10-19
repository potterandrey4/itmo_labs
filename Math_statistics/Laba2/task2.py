# Задание 2
import numpy as np
import scipy.stats as stats

# Параметры распределения Лапласа
mu = 0  # Параметр сдвига
b = 1   # Масштабирующий параметр (единичный)

n = 25  # Объем выборки
alpha = 0.05  # Уровень значимости

def generate_laplace_sample_and_ci(n, mu, b, alpha):
    """Генерация выборки из распределения Лапласа и построение доверительного интервала для параметра mu."""
    sample = np.random.laplace(mu, b, n)
    
    # Оценка медианы выборки
    sample_median = np.median(sample)
    
    # Построение доверительного интервала с использованием асимптотических свойств медианы
    z = stats.norm.ppf(1 - alpha / 2)
    ci_lower = sample_median - z * (b / np.sqrt(n))
    ci_upper = sample_median + z * (b / np.sqrt(n))
    
    return sample_median, (ci_lower, ci_upper)

# Проведение эксперимента 1000 раз
results_laplace = [generate_laplace_sample_and_ci(n, mu, b, alpha) for _ in range(1000)]

# Подсчет попаданий истинного значения mu в доверительный интервал
coverage_laplace = np.mean([ci[0] <= mu <= ci[1] for _, ci in results_laplace])

print(f'Покрытие для выборки из распределения Лапласа (n={n}): {coverage_laplace * 100:.2f}%')
