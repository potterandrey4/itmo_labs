import sys
import ast
import matplotlib
import matplotlib.pyplot as plt

matplotlib.use('Agg')


def parse_points(points_str):
    points = ast.literal_eval(points_str)
    x_values = [point[0] for point in points]
    y_values = [point[1] for point in points]
    return x_values, y_values


if __name__ == "__main__":
    dataArr = sys.argv


    for i in range(1, len(dataArr), 4):
        plt.figure()
        name = dataArr[i]
        rootX = float(dataArr[i + 1])
        rootY = float(dataArr[i + 2])
        dotsData = dataArr[i + 3]

        x_values, y_values = parse_points(dotsData)

        if name == "Функция":
            plt.plot(x_values, y_values, label=f'{name}', color='black')
        else:
            plt.plot(x_values, y_values, label=f'{name} method')
            plt.scatter(rootX, rootY, marker='o', label=f'Root {name} method')

            if name != "Simple Iteration":
                # print(name)
                # print(rootX, rootY)
                # print(x_values)
                # print(y_values)
                continue

        plt.xlabel('X')
        plt.ylabel('Y')
        plt.title(f'График {name} метода')
        plt.legend()
        plt.grid(True)

        plt.savefig(f'plots/plot_{name}.png')
