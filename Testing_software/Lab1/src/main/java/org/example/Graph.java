package org.example;

import java.util.*;

public class Graph {
    private final int vertices;
    private final List<List<Integer>> adjacencyList;
    private final List<Integer> visitedVertices;

    /**
     * Создает новый неориентированный граф с заданным количеством вершин.
     *
     * @param vertices количество вершин в графе
     */
    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        visitedVertices = new ArrayList<>();
    }

    /**
     * Добавляет ребро между вершинами в неориентированном графе.
     *
     * @param v первая вершина
     * @param w вторая вершина
     */
    public void addEdge(int v, int w) {
        // Проверка допустимых индексов
        if (v < 0 || v >= vertices || w < 0 || w >= vertices) {
            throw new IllegalArgumentException("Недопустимый индекс вершины");
        }

        // Добавляем ребра в обе стороны (неориентированный граф)
        adjacencyList.get(v).add(w);
        adjacencyList.get(w).add(v);
    }

    /**
     * Выполняет обход графа в глубину (DFS) начиная с заданной вершины.
     *
     * @param startVertex начальная вершина
     * @return список вершин в порядке их посещения
     */
    public List<Integer> depthFirstSearch(int startVertex) {
        // Проверка допустимости индекса
        if (startVertex < 0 || startVertex >= vertices) {
            throw new IllegalArgumentException("Недопустимый индекс начальной вершины");
        }

        // Сбросить список посещенных вершин
        visitedVertices.clear();

        // Массив для отслеживания посещенных вершин
        boolean[] visited = new boolean[vertices];

        // Запустить рекурсивный DFS
        dfsRecursive(startVertex, visited);

        return new ArrayList<>(visitedVertices);
    }

    /**
     * Рекурсивная вспомогательная функция для DFS.
     *
     * @param vertex  текущая вершина
     * @param visited массив отметок о посещении вершин
     */
    private void dfsRecursive(int vertex, boolean[] visited) {
        // Отмечаем текущую вершину как посещенную
        visited[vertex] = true;

        // Добавляем в список посещенных вершин
        visitedVertices.add(vertex);

        // Рекурсивно посещаем все соседние вершины
        for (Integer neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor]) {
                dfsRecursive(neighbor, visited);
            }
        }
    }

    /**
     * Возвращает количество вершин в графе.
     *
     * @return количество вершин
     */
    public int getVertices() {
        return vertices;
    }

    /**
     * Возвращает список смежности для указанной вершины.
     *
     * @param vertex индекс вершины
     * @return список смежных вершин
     */
    public List<Integer> getAdjacencyList(int vertex) {
        if (vertex < 0 || vertex >= vertices) {
            throw new IllegalArgumentException("Недопустимый индекс вершины");
        }
        return new ArrayList<>(adjacencyList.get(vertex));
    }
}
