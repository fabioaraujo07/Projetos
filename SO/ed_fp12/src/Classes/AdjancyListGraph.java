package Classes;



import Interfaces.GraphADT;

import java.util.Iterator;

public class AdjancyListGraph<T> implements GraphADT<T> {

    private LinkedUnorderedList<T>[] adjacencyList; // Lista de adjacência
    private T[] vertices; // Vértices armazenados
    private int numVertices; // Número atual de vértices
    private int capacity; // Capacidade máxima

    private final int DEFAULT_CAPACITY = 10;

    /**
     * Construtor da classe, inicializando com capacidade padrão.
     */
    public AdjancyListGraph() {
        this.capacity = DEFAULT_CAPACITY;
        this.numVertices = 0;
        this.vertices = (T[]) new Object[capacity];
        this.adjacencyList = new LinkedUnorderedList[capacity];

        // Inicializa cada lista de adjacência
        for (int i = 0; i < capacity; i++) {
            adjacencyList[i] = new LinkedUnorderedList<>();
        }
    }

    private void expandCapacity() {
        capacity *= 2;

        T[] newVertices = (T[]) new Object[capacity];
        LinkedUnorderedList<T>[] newAdjacencyList = new LinkedUnorderedList[capacity];

        for (int i = 0; i < numVertices; i++) {
            newVertices[i] = vertices[i];
            newAdjacencyList[i] = adjacencyList[i];
        }

        for (int i = numVertices; i < capacity; i++) {
            newAdjacencyList[i] = new LinkedUnorderedList<>();
        }

        vertices = newVertices;
        adjacencyList = newAdjacencyList;
    }


    @Override
    public void addVertex(T vertex) {
        if (numVertices == capacity) {
            expandCapacity();
        }
        vertices[numVertices] = vertex;
        numVertices++;
    }

    @Override
    public void removeVertex(T vertex) {
        int index = getIndex(vertex);
        if (index == -1) {
            throw new IllegalArgumentException("Vertex not found.");
        }

        // Remove conexões para o vértice de todas as listas
        for (int i = 0; i < numVertices; i++) {
            adjacencyList[i].remove(vertex); // Remove o vértice das listas adjacentes
        }

        // Remove todas as conexões desse vértice
        adjacencyList[index].clear();

        // Reorganizar os vértices e listas para preencher o espaço
        for (int i = index; i < numVertices - 1; i++) {
            vertices[i] = vertices[i + 1];
            adjacencyList[i] = adjacencyList[i + 1];
        }

        // Limpar última posição (não recria desnecessariamente)
        vertices[numVertices - 1] = null;
        adjacencyList[numVertices - 1] = null;

        numVertices--;
    }


    @Override
    public void addEdge(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (index1 == -1 || index2 == -1) {
            throw new IllegalArgumentException("One or both vertices not found.");
        }

        // Adiciona as conexões bidirecionais
        adjacencyList[index1].addToRear(vertex2);
        adjacencyList[index2].addToRear(vertex1);
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (index1 == -1 || index2 == -1) {
            throw new IllegalArgumentException("One or both vertices not found.");
        }

        adjacencyList[index1].remove(vertex2);
        adjacencyList[index2].remove(vertex1);
    }

    @Override
    public Iterator iteratorBFS(T startVertex) {
        int startIndex = getIndex(startVertex);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Vertex not found.");
        }

        LinkedQueue<Integer> queue = new LinkedQueue<>();
        boolean[] visited = new boolean[numVertices];
        LinkedUnorderedList<T> result = new LinkedUnorderedList<>();

        queue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            result.addToRear(vertices[current]);

            for (T neighbor : adjacencyList[current]) {
                int neighborIndex = getIndex(neighbor);
                if (!visited[neighborIndex]) {
                    visited[neighborIndex] = true;
                    queue.enqueue(neighborIndex);
                }
            }
        }

        return result.iterator();
    }

    @Override
    public Iterator iteratorDFS(T startVertex) {
        int startIndex = getIndex(startVertex);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Vertex not found.");
        }

        LinkedStack<Integer> stack = new LinkedStack<>();
        boolean[] visited = new boolean[numVertices];
        LinkedUnorderedList<T> result = new LinkedUnorderedList<>();

        stack.push(startIndex);
        visited[startIndex] = true;

        while (!stack.isEmpty()) {
            int current = stack.pop();
            result.addToRear(vertices[current]);

            for (T neighbor : adjacencyList[current]) {
                int neighborIndex = getIndex(neighbor);
                if (!visited[neighborIndex]) {
                    visited[neighborIndex] = true;
                    stack.push(neighborIndex);
                }
            }
        }

        return result.iterator();
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        // Implementação de BFS para encontrar o caminho mais curto pode ser feita aqui.
        return null;
    }

    @Override
    public boolean isEmpty() {
        return numVertices == 0;
    }

    @Override
    public boolean isConnected() {
        if (numVertices == 0) {
            return true;
        }

        Iterator<T> bfs = iteratorBFS(vertices[0]);
        int count = 0;

        while (bfs.hasNext()) {
            bfs.next();
            count++;
        }

        return count == numVertices;
    }

    @Override
    public int size() {
        return numVertices;
    }

    private int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

}
