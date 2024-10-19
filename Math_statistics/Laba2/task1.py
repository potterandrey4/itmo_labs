import numpy as np
import scipy.stats as stats

# Параметры для двух нормальных распределений
mu1, sigma1 = 0, 1  # Среднее и стандартное отклонение для первого распределения
mu2, sigma2 = 0, 1  # Среднее и стандартное отклонение для второго распределения

n_small = 25    # Объем маленькой выборки
n_large = 10000  # Объем большой выборки
alpha = 0.05    # Уровень значимости для 95% доверительного интервала

def generate_sample_and_ci(n, mu1, sigma1, mu2, sigma2, alpha):
    """Функция для генерации выборок и построения доверительного интервала."""
    # Генерация выборок
    sample1 = np.random.normal(mu1, sigma1, n)
    sample2 = np.random.normal(mu2, sigma2, n)
    
    # Разница средних
    mean_diff = np.mean(sample1) - np.mean(sample2)
    
    # Стандартная ошибка разности средних
    se_diff = np.sqrt((sigma1**2 / n) + (sigma2**2 / n))
    
    # Параметры для доверительного интервала
    z = stats.norm.ppf(1 - alpha / 2)
    
    # Доверительный интервал
    ci_lower = mean_diff - z * se_diff
    ci_upper = mean_diff + z * se_diff
    
    return mean_diff, (ci_lower, ci_upper)

# Проведение эксперимента 1000 раз для маленькой выборки
results_small = [generate_sample_and_ci(n_small, mu1, sigma1, mu2, sigma2, alpha) for _ in range(1000)]

# Проведение эксперимента 1000 раз для большой выборки
results_large = [generate_sample_and_ci(n_large, mu1, sigma1, mu2, sigma2, alpha) for _ in range(1000)]

# Подсчет попаданий реальной разницы средних в доверительный интервал
real_diff = mu1 - mu2

coverage_small = np.mean([ci[0] <= real_diff <= ci[1] for _, ci in results_small])
coverage_large = np.mean([ci[0] <= real_diff <= ci[1] for _, ci in results_large])

print(f'Покрытие для маленькой выборки (n={n_small}): {coverage_small * 100:.2f}%')
print(f'Покрытие для большой выборки (n={n_large}): {coverage_large * 100:.2f}%')
