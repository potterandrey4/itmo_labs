import random

ways = [
    [0, 4, 5, 3, 8],
    [4, 0, 7, 6, 8],
    [5, 7, 0, 7, 9],
    [3, 6, 7, 0, 9],
    [8, 8, 9, 9, 0]
]


POPULATION_SIZE = 4
CITIES_SIZE = 5
ITERATIONS = 100

class Route:
    def __init__(self, cities):
        self.cities = cities

    def get_cost(self):
        cost = 0
        for i in range(len(self.cities)):
            from_city = self.cities[-1] if i == 0 else self.cities[i - 1]
            to_city = self.cities[i]
            cost += ways[from_city][to_city]
        return cost

def generate_initial_routes():
    initial_routes = [Route([0] + list(range(1, CITIES_SIZE)))]
    for _ in range(POPULATION_SIZE - 1):
        initial_routes.append(Route([0] + random.sample(range(1, CITIES_SIZE), CITIES_SIZE - 1)))
    return initial_routes

def create_new_routes(routes):
    new_routes = []
    for i in range(0, len(routes), 2):
        if i + 1 < len(routes):
            parent1 = routes[i]
            parent2 = routes[i + 1]
            breakpoint = random.randint(1, CITIES_SIZE - 1)
            child1_cities = parent1.cities[:breakpoint] + [city for city in parent2.cities if city not in parent1.cities[:breakpoint]]
            child2_cities = parent2.cities[:breakpoint] + [city for city in parent1.cities if city not in parent2.cities[:breakpoint]]
            
            if random.randint(0, 100) <= 1:
                idx1, idx2 = random.sample(range(CITIES_SIZE), 2)
                child1_cities[idx1], child1_cities[idx2] = child1_cities[idx2], child1_cities[idx1]

            new_routes.append(Route(child1_cities))
            new_routes.append(Route(child2_cities))
    return new_routes

routes = generate_initial_routes()

for _ in range(ITERATIONS):
    random.shuffle(routes)
    new_routes = create_new_routes(routes)
    routes.extend(new_routes)
    routes.sort(key=lambda route: route.get_cost())
    routes = routes[:POPULATION_SIZE]

best_route = routes[0]
print(f"Оптиммальный путь: {[city + 1 for city in best_route.cities]}. Стоимость: {best_route.get_cost()}")
