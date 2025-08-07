import math

# Функции для генерации заглушек
TRIG_FUNCS = {
    "Sin": math.sin,
    "Cos": math.cos,
    "Tan": math.tan,
    "Cot": lambda x: 1 / math.tan(x),
    "Sec": lambda x: 1 / math.cos(x),
    "Csc": lambda x: 1 / math.sin(x),
}

# Чтение X из CSV (x, f(x))
def read_points(path):
    points = []
    with open(path, "r") as f:
        f.readline()  # Пропускаем заголовок
        for line in f:
            if not line.strip():
                continue
            x_str, _ = line.strip().split(",")
            x = float(x_str)
            if x <= 0:
                points.append(x)
    return sorted(set(points))

# Генерация текста заглушки
def generate_stub(func_name, func, xs):
    result = f"""package itmo.testing.function.stub

import itmo.testing.function.Calculable

class {func_name}Stub : Calculable {{
    override fun calculate(x: Double): Double = when (x) {{\n"""
    
    handled = set()

    for x in xs:
        # Для SinStub добавим также (π/2 - x)
        if func_name == "Sin":
            for val in (x, math.pi/2 - x):
                if val in handled:
                    continue
                try:
                    y = func(val)
                    if math.isnan(y) or math.isinf(y):
                        continue
                    result += f"        {val} -> {round(y, 6)}\n"
                    handled.add(val)
                except Exception:
                    continue
        else:
            if x in handled:
                continue
            try:
                y = func(x)
                if math.isnan(y) or math.isinf(y):
                    continue
                result += f"        {x} -> {round(y, 6)}\n"
                handled.add(x)
            except Exception:
                continue

    result += "        else -> Double.NaN\n"
    result += "    }\n}"
    return result


# Главная функция
def main():
    xs = read_points("src/main/resources/system_dots.csv")
    for name, func in TRIG_FUNCS.items():
        stub_code = generate_stub(name, func, xs)
        print(f"\n// === {name}Stub.kt ===")
        print(stub_code)

if __name__ == "__main__":
    main()
