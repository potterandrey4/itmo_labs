import os
import pandas as pd
import matplotlib.pyplot as plt

# Папка, содержащая CSV-файлы
CSV_DIR = "build"
PLOT_DIR = "plots"

os.makedirs(PLOT_DIR, exist_ok=True)

def plot_csv(file_path):
    name = os.path.splitext(os.path.basename(file_path))[0]
    df = pd.read_csv(file_path, sep=';', engine='python')

    if 'x' not in df.columns or df.columns[1] not in df.columns:
        print(f"Skipping {file_path}: incorrect format")
        return

    x = df['x']
    y = df[df.columns[1]]

    plt.figure()
    plt.plot(x, y, label=name)
    plt.title(f"Function plot: {name}")
    plt.xlabel("x")
    plt.ylabel("f(x)")
    plt.grid(True)
    plt.legend()
    plt.savefig(f"{PLOT_DIR}/{name}.png")
    plt.close()

# Обрабатываем все .csv файлы в директории
for filename in os.listdir(CSV_DIR):
    if filename.endswith(".csv"):
        plot_csv(os.path.join(CSV_DIR, filename))

print("All plots generated in:", PLOT_DIR)