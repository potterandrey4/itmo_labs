# Импорт необходимых библиотек
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Загрузка данных из CSV файла
data = pd.read_csv('sex_bmi_smokers.csv')

# Сравнение количества курящих мужчин и некурящих женщин
smoking_men = data[(data['sex'] == 'male') & (data['smoker'] == 'yes')]
non_smoking_men = data[(data['sex'] == 'male') & (data['smoker'] == 'no')]
smoking_women = data[(data['sex'] == 'female') & (data['smoker'] == 'yes')]
non_smoking_women = data[(data['sex'] == 'female') & (data['smoker'] == 'no')]

print("\nКоличество курящих мужчин:", len(smoking_men))
print("Количество некурящих женщин:", len(non_smoking_women))
print("Разница:", len(non_smoking_women) - len(smoking_men))

# Выборочные статистики для всех наблюдателей
mean_bmi = data['bmi'].mean()
variance_bmi = data['bmi'].var()
median_bmi = data['bmi'].median()
quantile_3_5 = data['bmi'].quantile(3 / 5)

print("\nОбщие выборочные статистики для ИМТ:")
print(f"Среднее: {mean_bmi:.2f}")
print(f"Выборочная дисперсия: {variance_bmi:.2f}")
print(f"Медиана: {median_bmi:.2f}")
print(f"Квантиль 3/5: {quantile_3_5:.2f}")

# Выборочные статистики для курящих мужчин
mean_bmi_smoking_men = smoking_men['bmi'].mean()
variance_bmi_smoking_men = smoking_men['bmi'].var()
median_bmi_smoking_men = smoking_men['bmi'].median()
quantile_3_5_smoking_men = smoking_men['bmi'].quantile(3 / 5)

print("\nВыборочные статистики для курящих мужчин:")
print(f"Среднее: {mean_bmi_smoking_men:.2f}")
print(f"Выборочная дисперсия: {variance_bmi_smoking_men:.2f}")
print(f"Медиана: {median_bmi_smoking_men:.2f}")
print(f"Квантиль 3/5: {quantile_3_5_smoking_men:.2f}")

## Выборочные статистики для некурящих мужчин
mean_bmi_non_smoking_men = non_smoking_men['bmi'].mean()
variance_bmi_non_smoking_men = non_smoking_men['bmi'].var()
median_bmi_non_smoking_men = non_smoking_men['bmi'].median()
quantile_3_5_non_smoking_men = non_smoking_men['bmi'].quantile(3 / 5)

print("\nВыборочные статистики для некурящих мужчин:")
print(f"Среднее: {mean_bmi_non_smoking_men:.2f}")
print(f"Выборочная дисперсия: {variance_bmi_non_smoking_men:.2f}")
print(f"Медиана: {median_bmi_non_smoking_men:.2f}")
print(f"Квантиль 3/5: {quantile_3_5_non_smoking_men:.2f}")

## Выборочные статистики для курящих женщин
mean_bmi_smoking_women = smoking_women['bmi'].mean()
variance_bmi_smoking_women = smoking_women['bmi'].var()
median_bmi_smoking_women = smoking_women['bmi'].median()
quantile_3_5_smoking_women = smoking_women['bmi'].quantile(3 / 5)

print("\nВыборочные статистики для курящих женщин:")
print(f"Среднее: {mean_bmi_smoking_women:.2f}")
print(f"Выборочная дисперсия: {variance_bmi_smoking_women:.2f}")
print(f"Медиана: {median_bmi_smoking_women:.2f}")
print(f"Квантиль 3/5: {quantile_3_5_smoking_women:.2f}")

## Выборочные статистики для некурящих женщин
mean_bmi_non_smoking_women = non_smoking_women['bmi'].mean()
variance_non_smoking_bmi_women = non_smoking_women['bmi'].var()
median_bmi_non_smoking_women = non_smoking_women['bmi'].median()
quantile_3_5_non_smoking_women = non_smoking_women['bmi'].quantile(3 / 5)

print("\nВыборочные статистики для некурящих женщин:")
print(f"Среднее: {mean_bmi_non_smoking_women:.2f}")
print(f"Выборочная дисперсия: {variance_non_smoking_bmi_women:.2f}")
print(f"Медиана: {median_bmi_non_smoking_women:.2f}")
print(f"Квантиль 3/5: {quantile_3_5_non_smoking_women:.2f}")

## Построение эмпирической функции распределения для всех наблюдателей
plt.figure(figsize=(10, 6))
sns.ecdfplot(data['bmi'])
plt.title('Эмпирическая функция распределения для всех наблюдателей')
plt.xlabel('Индекс массы тела ')
plt.ylabel('Доля наблюдателей')
plt.grid()
plt.show()

## Построение эмпирической функции распределения для курящих мужчин
plt.figure(figsize=(10, 6))
sns.ecdfplot(smoking_men['bmi'])
plt.title('Эмпирическая функция распределения для курящих мужчин')
plt.xlabel('Индекс массы тела ')
plt.ylabel('Доля наблюдателей')
plt.grid()
plt.show()

## Построение эмпирической функции распределения для некурящих мужчин
plt.figure(figsize=(10, 6))
sns.ecdfplot(non_smoking_men['bmi'])
plt.title('Эмпирическая функция распределения для некурящих мужчин')
plt.xlabel('Индекс массы тела')
plt.ylabel('Доля наблюдателей')
plt.grid()
plt.show()

## Построение эмпирической функции распределения для курящих женщин
plt.figure(figsize=(10, 6))
sns.ecdfplot(smoking_women['bmi'])
plt.title('Эмпирическая функция распределения для курящих женщин')
plt.xlabel('Индекс массы тела')
plt.ylabel('Доля наблюдателей')
plt.grid()
plt.show()

## Построение эмпирической функции распределения для некурящих женщин
plt.figure(figsize=(10, 6))
sns.ecdfplot(non_smoking_women['bmi'])
plt.title('Эмпирическая функция распределения для некурящих женщин')
plt.xlabel('Индекс массы тела')
plt.ylabel('Доля наблюдателей')
plt.grid()
plt.show()

# Построение гистограммы для всех наблюдателей
plt.figure(figsize=(10, 6))
sns.histplot(data['bmi'], kde=True, bins=30)
plt.title('Гистограмма ИМТ для всех наблюдателей')
plt.xlabel('Индекс массы тела')
plt.ylabel('Частота')
plt.grid()
plt.show()

# Построение гистограммы для курящих мужчин
plt.figure(figsize=(10, 6))
sns.histplot(smoking_men['bmi'], kde=True, bins=30)
plt.title('Гистограмма ИМТ для курящих мужчин')
plt.xlabel('Индекс массы тела')
plt.ylabel('Частота')
plt.grid()
plt.show()

# Построение гистограммы для некурящих мужчин
plt.figure(figsize=(10, 6))
sns.histplot(non_smoking_men['bmi'], kde=True, bins=30)
plt.title('Гистограмма ИМТ для некурящих мужчин')
plt.xlabel('Индекс массы тела')
plt.ylabel('Частота')
plt.grid()
plt.show()

# Построение гистограммы для курящих женщин
plt.figure(figsize=(10, 6))
sns.histplot(smoking_women['bmi'], kde=True, bins=30)
plt.title('Гистограмма ИМТ для курящих женщин')
plt.xlabel('Индекс массы тела')
plt.ylabel('Частота')
plt.grid()
plt.show()

# Построение гистограммы для некурящих женщин
plt.figure(figsize=(10, 6))
sns.histplot(non_smoking_women['bmi'], kde=True, bins=30)
plt.title('Гистограмма ИМТ для некурящих женщин')
plt.xlabel('Индекс массы тела')
plt.ylabel('Частота')
plt.grid()
plt.show()

# Построение boxplot для всех наблюдателей
plt.figure(figsize=(10, 6))
sns.boxplot(y='bmi', data=data)
plt.title('Boxplot для ИМТ всех наблюдателей')
plt.ylabel('Индекс массы тела')
plt.grid()
plt.show()

# Построение boxplot для курящих мужчин
plt.figure(figsize=(10, 6))
sns.boxplot(y='bmi', data=smoking_men)
plt.title('Boxplot для ИМТ курящих мужчин')
plt.ylabel('Индекс массы тела')
plt.grid()
plt.show()

# Построение boxplot для некурящих мужчин
plt.figure(figsize=(10, 6))
sns.boxplot(y='bmi', data=non_smoking_men)
plt.title('Boxplot для ИМТ некурящих мужчин')
plt.ylabel('Индекс массы тела')
plt.grid()
plt.show()

# Построение boxplot для курящих женщин
plt.figure(figsize=(10, 6))
sns.boxplot(y='bmi', data=smoking_women)
plt.title('Boxplot для ИМТ курящих женщин')
plt.ylabel('Индекс массы тела')
plt.grid()
plt.show()

# Построение boxplot для некурящих женщин
plt.figure(figsize=(10, 6))
sns.boxplot(y='bmi', data=non_smoking_women)
plt.title('Boxplot для ИМТ некурящих женщин')
plt.ylabel('Индекс массы тела')
plt.grid()
plt.show()
