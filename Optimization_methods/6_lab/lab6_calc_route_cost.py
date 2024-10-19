ways = [
    [0, 10, 15, 20, 25],
    [10, 0, 15, 20, 25],
    [15, 35, 0, 30, 20],
    [20, 25, 30, 0, 15],
    [25, 30, 20, 15, 0]
]

# Функция для расчета стоимости маршрута
def calculate_route_cost(route):
    cost = 0
    for i in range(1, len(route)):
        from_city = route[i - 1] - 1  # Индексы городов (уменьшение на 1)
        to_city = route[i] - 1
        cost += ways[from_city][to_city]
    
    # Добавляем возврат в начальный город (из последнего города в первый)
    cost += ways[route[-1] - 1][route[0] - 1]
    
    return cost

# Ввод маршрута через пробелы
while True:
    route_input = input("Введите маршрут через пробел (например: 1 4 5 2 3): ")
    route = list(map(int, (i for i in route_input)))
    
    # Расчет стоимости маршрута
    cost = calculate_route_cost(route)
    print(f"Стоимость маршрута {route}: {cost}")