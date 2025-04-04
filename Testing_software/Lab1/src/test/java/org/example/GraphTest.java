package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class GraphTest {
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph(5);
    }

    @Test
    void testGraphCreation() {
        assertEquals(5, graph.getVertices());
        for (int i = 0; i < 5; i++) {
            assertTrue(graph.getAdjacencyList(i).isEmpty());
        }
    }

    @Test
    void testAddEdge() {
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);

        // Test adjacency list for vertex 0
        List<Integer> adjList0 = graph.getAdjacencyList(0);
        assertEquals(2, adjList0.size());
        assertTrue(adjList0.contains(1));
        assertTrue(adjList0.contains(2));

        // Test adjacency list for vertex 1
        List<Integer> adjList1 = graph.getAdjacencyList(1);
        assertEquals(2, adjList1.size());
        assertTrue(adjList1.contains(0));
        assertTrue(adjList1.contains(3));
    }

    @Test
    void testAddEdgeWithInvalidVertex() {
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(-1, 2));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(0, 5));
    }

    @Test
    void testDepthFirstSearch() {
        // Create a simple graph
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 4);

        List<Integer> dfsResult = graph.depthFirstSearch(0);

        // Verify all vertices are visited
        assertEquals(5, dfsResult.size());
        assertTrue(dfsResult.contains(0));
        assertTrue(dfsResult.contains(1));
        assertTrue(dfsResult.contains(2));
        assertTrue(dfsResult.contains(3));
        assertTrue(dfsResult.contains(4));

        // Verify the first vertex in the result is the start vertex
        assertEquals(0, dfsResult.get(0));
    }

    @Test
    void testDfsOnDisconnectedGraph() {
        // Create a disconnected graph
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);

        List<Integer> dfsResult = graph.depthFirstSearch(0);

        // Verify only the connected component is visited
        assertEquals(3, dfsResult.size());
        assertTrue(dfsResult.contains(0));
        assertTrue(dfsResult.contains(1));
        assertTrue(dfsResult.contains(2));
        assertFalse(dfsResult.contains(3));
        assertFalse(dfsResult.contains(4));
    }

    @Test
    void testDfsWithInvalidStartVertex() {
        assertThrows(IllegalArgumentException.class, () -> graph.depthFirstSearch(-1));
        assertThrows(IllegalArgumentException.class, () -> graph.depthFirstSearch(5));
    }

    @Test
    void testGetAdjacencyListWithInvalidVertex() {
        // Test with negative vertex index
        assertThrows(IllegalArgumentException.class, () -> graph.getAdjacencyList(-1));

        // Test with vertex index exceeding graph size
        assertThrows(IllegalArgumentException.class, () -> graph.getAdjacencyList(5));
    }

    @Test
    void testDfsOrder() {
        // Create a linear graph
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        List<Integer> dfsResult = graph.depthFirstSearch(0);

        assertEquals(5, dfsResult.size());
        assertEquals(0, dfsResult.get(0));
        assertEquals(1, dfsResult.get(1));
        assertEquals(2, dfsResult.get(2));
        assertEquals(3, dfsResult.get(3));
        assertEquals(4, dfsResult.get(4));
    }

    @Test
    void testDfsFromDifferentStartVertices() {
        // Create a star graph
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);

        // DFS from vertex 0
        List<Integer> dfsResult0 = graph.depthFirstSearch(0);
        assertEquals(5, dfsResult0.size());
        assertEquals(0, dfsResult0.get(0));

        // DFS from vertex 2
        List<Integer> dfsResult2 = graph.depthFirstSearch(2);
        assertEquals(5, dfsResult2.size());
        assertEquals(2, dfsResult2.get(0));
    }

    @Test
    void testSingleVertexGraph() {
        Graph singleVertexGraph = new Graph(1);
        assertEquals(1, singleVertexGraph.getVertices());
        assertTrue(singleVertexGraph.getAdjacencyList(0).isEmpty());

        List<Integer> dfsResult = singleVertexGraph.depthFirstSearch(0);
        assertEquals(1, dfsResult.size());
        assertEquals(0, dfsResult.get(0));
    }

    @Test
    void testCyclicGraph() {
        // Create a cyclic graph
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);

        List<Integer> dfsResult = graph.depthFirstSearch(0);

        assertEquals(3, dfsResult.size());
        assertTrue(dfsResult.contains(0));
        assertTrue(dfsResult.contains(1));
        assertTrue(dfsResult.contains(2));
    }

//    @Test
//    void testComplexGraph() {
//        // Create a more complex graph with multiple paths
//        Graph complexGraph = new Graph(7);
//        complexGraph.addEdge(0, 1);
//        complexGraph.addEdge(0, 2);
//        complexGraph.addEdge(1, 3);
//        complexGraph.addEdge(2, 4);
//        complexGraph.addEdge(2, 5);
//        complexGraph.addEdge(3, 6);
//        complexGraph.addEdge(4, 6);
//        complexGraph.addEdge(5, 6);
//
//        List<Integer> dfsResult = complexGraph.depthFirstSearch(0);
//
//        assertEquals(7, dfsResult.size());
//        assertEquals(0, dfsResult.get(0));
//    }
}