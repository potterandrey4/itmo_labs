import sys
import matplotlib
import matplotlib.pyplot as plt

matplotlib.use('Agg')


def parse_list(arg):
    return [float(x) for x in arg.strip('[]').split(', ')]


if __name__ == "__main__":
    num_graphs = (len(sys.argv) - 1) // 6
    print(sys.argv)

    for i in range(num_graphs):
        name = sys.argv[1 + i * 6]
        x_values = parse_list(sys.argv[2 + i * 6])
        y_values = parse_list(sys.argv[3 + i * 6])

        if name == "Функция":
            plt.plot(x_values, y_values, label=f'{name}', color='black')
        else:
            plt.plot(x_values, y_values, label=f'{name}')
            # plt.scatter(root_x, root_y, marker='o', label=f'Root {name}')

    plt.xlabel('X')
    plt.ylabel('Y')
    plt.title('Графики методов')
    plt.legend()
    plt.grid(True)
    plt.savefig('plot.png')
