# https://docs.google.com/document/d/1C487J2zN6rxlkaPkiUyKG8bWa6DFt7-Cu-3yWPSeul8/edit?hl=ru&pli=1&tab=t.0

import numpy as np
import matplotlib.pyplot as plt

# Исходное сообщение
message = "11000100 11000000 11000100".replace(" ", "")

# Параметры канала
bit_rate = 100e6  # 100 Мбит/с
bit_duration = 1 / bit_rate  # Длительность одного бита

# Генерация временной оси
samples_per_bit = 100  # Количество отсчетов на бит (для детализации графиков)
t = np.linspace(0, len(message) * bit_duration, len(message) * samples_per_bit)

# Функция для манчестерского кодирования
def manchester_encoding(bits):
    signal = np.zeros(len(bits) * samples_per_bit)
    for i, bit in enumerate(bits):
        t_start = i * samples_per_bit
        mid = t_start + samples_per_bit // 2
        if bit == "1":
            signal[t_start:mid] = 1
            signal[mid:t_start + samples_per_bit] = -1
        else:
            signal[t_start:mid] = -1
            signal[mid:t_start + samples_per_bit] = 1
    return signal

# Генерация манчестерского сигнала
manchester_signal = manchester_encoding(message)

# Построение временной диаграммы
plt.figure(figsize=(10, 4))
plt.plot(t, manchester_signal, drawstyle='steps-pre', label="Манчестерский код")
plt.xlabel("Время (с)")
plt.ylabel("Амплитуда")
plt.title("Манчестерское кодирование")
plt.grid(True)
plt.legend()
plt.show()

# Функция для NRZ-I кодирования
def nrz_i_encoding(bits):
    signal = np.zeros(len(bits) * samples_per_bit)
    current_level = 1  # Начальное состояние сигнала
    for i, bit in enumerate(bits):
        t_start = i * samples_per_bit
        if bit == "1":
            current_level *= -1  # Инвертирование уровня для "1"
        signal[t_start:t_start + samples_per_bit] = current_level
    return signal

# Генерация NRZ-I сигнала
nrz_i_signal = nrz_i_encoding(message)

# Построение временной диаграммы NRZ-I
plt.figure(figsize=(10, 4))
plt.plot(t, nrz_i_signal, drawstyle='steps-pre', label="NRZ-I код")
plt.xlabel("Время (с)")
plt.ylabel("Амплитуда")
plt.title("NRZ-I кодирование")
plt.grid(True)
plt.legend()
plt.show()

# Функция для AMI кодирования
def ami_encoding(bits):
    signal = np.zeros(len(bits) * samples_per_bit)
    current_level = 1  # Начальное состояние сигнала
    for i, bit in enumerate(bits):
        t_start = i * samples_per_bit
        if bit == "1":
            signal[t_start:t_start + samples_per_bit] = current_level
            current_level *= -1  # Чередование уровней для "1"
        else:
            signal[t_start:t_start + samples_per_bit] = 0  # "0" передается как отсутствие сигнала
    return signal

# Генерация AMI сигнала
ami_signal = ami_encoding(message)

# Построение временной диаграммы AMI
plt.figure(figsize=(10, 4))
plt.plot(t, ami_signal, drawstyle='steps-pre', label="AMI код")
plt.xlabel("Время (с)")
plt.ylabel("Амплитуда")
plt.title("AMI кодирование")
plt.grid(True)
plt.legend()
plt.show()
