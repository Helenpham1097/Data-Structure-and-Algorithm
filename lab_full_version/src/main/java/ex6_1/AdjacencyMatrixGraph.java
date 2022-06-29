package ex6_1;

import java.util.*;
import java.util.stream.Collectors;

public class AdjacencyMatrixGraph<E> implements GraphADT<E> {

    public static void main(String[] args) {
        AdjacencyMatrixGraph matrixGraph = new AdjacencyMatrixGraph();

        System.out.println("Return true when adjacency matrix has no vertices nor edges");
        System.out.println(matrixGraph.isEmpty());
        System.out.println();

        System.out.println("Add vertices: auckland, wellington, christchurch, fiji, samoa and some edges");
        Vertex<String> auckland = matrixGraph.addVertex("Auc");
        Vertex<String> wellington = matrixGraph.addVertex("Wel");
        Vertex<String> christchurch = matrixGraph.addVertex("Chr");
        Vertex<String> fiji = matrixGraph.addVertex("Fij");
        Vertex<String> samoa = matrixGraph.addVertex("Sam");

        matrixGraph.addEdge(auckland, wellington);
        matrixGraph.addEdge(auckland, christchurch);
        matrixGraph.addEdge(auckland, fiji);
        matrixGraph.addEdge(auckland, samoa);
        matrixGraph.addEdge(wellington, christchurch);
        matrixGraph.addEdge(fiji, christchurch);
        Edge<String> fijiSamoaEdge =matrixGraph.addEdge(fiji, samoa);
        System.out.println();

        System.out.println(matrixGraph.displayAdjacencyMatrix());
        System.out.println();

        System.out.println("Edge set of the adjacency matrix");
        System.out.println(matrixGraph.edgeSet());
        System.out.println();

        System.out.println("Matrix graph contains " + fijiSamoaEdge + ": " + matrixGraph.containsEdge(fijiSamoaEdge));
        System.out.println();

        System.out.println("Remove edge" + fijiSamoaEdge + ": " + matrixGraph.removeEdge(fijiSamoaEdge));
        System.out.println();

        System.out.println("Graph after remove edge: " + fijiSamoaEdge);
        System.out.println(matrixGraph.displayAdjacencyMatrix());

        System.out.println("Graph contains " + fijiSamoaEdge + ": " + matrixGraph.containsEdge(fijiSamoaEdge));
        System.out.println();

        System.out.println("Creating new matrix graph");
        AdjacencyMatrixGraph matrixGraph2 = new AdjacencyMatrixGraph();
        Vertex<String> brisbane = matrixGraph2.addVertex("Bri");
        Vertex<String> sydney = matrixGraph2.addVertex("Syd");
        Vertex<String> melbourne = matrixGraph2.addVertex("Mel");

        matrixGraph2.addEdge(brisbane, sydney);
        matrixGraph2.addEdge(sydney, melbourne);

        System.out.println(matrixGraph2.displayAdjacencyMatrix());
        System.out.println();

        System.out.println("Add matrix graph 2 to current graph");
        matrixGraph.addGraph(matrixGraph2);
        System.out.println(matrixGraph.displayAdjacencyMatrix());
        System.out.println();

        System.out.println("Remove vertex FiJi: " + matrixGraph.removeVertex(fiji));
        System.out.println(matrixGraph.displayAdjacencyMatrix());

    }

    private Set<Vertex<E>> vertices;
    private Edge<E>[][] adjacencyMatrix;

    public AdjacencyMatrixGraph() {
        this.vertices = new HashSet<>();
        this.adjacencyMatrix = new Edge[0][0];
    }

    @Override
    public void clear() {
        vertices.clear();
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public Set<Vertex<E>> vertexSet() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public Set<Edge<E>> edgeSet() {
        Set<Edge<E>> edgeSet = new HashSet<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] != null) {
                    edgeSet.add(adjacencyMatrix[i][j]);
                }
            }
        }
        return edgeSet;
    }

    @Override
    public <F extends E> void addGraph(GraphADT<F> graph) {
        List<Vertex<F>> verticesFromGraph = new ArrayList<>(graph.vertexSet());
        for(Vertex<F> vertex:verticesFromGraph){
            this.addVertex(vertex.getUserObject());
        }
        Set<Edge<F>> edges = graph.edgeSet();
        for(Edge<F> edge : edges){
            this.addEdge((Vertex<E>) edge.endVertices()[0], (Vertex<E>) edge.endVertices()[1]);
        }
    }

    @Override
    public Vertex<E> addVertex(E element) {
        Vertex vertex = new VertexImpl(element);
        vertices.add(new VertexImpl(element));
        updateAdjacencyMatrix();
        return vertex;
    }

    private void updateAdjacencyMatrix() {
        Edge<E>[][] currentAdjacency = this.adjacencyMatrix;
        Edge<E>[][] newAdjacencyMatrix = new Edge[vertices.size()][vertices.size()];
        for (int i = 0; i < currentAdjacency.length; i++) {
            System.arraycopy(currentAdjacency[i], 0, newAdjacencyMatrix[i], 0, currentAdjacency[i].length);
        }
        this.adjacencyMatrix = newAdjacencyMatrix;
    }

    @Override
    public Edge<E> addEdge(Vertex<E> vertex0, Vertex<E> vertex1) {
        Edge<E> edge = new EdgeImpl(vertex0, vertex1);
        List<Vertex<E>> vertexList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices.size(); i++) {
            if (vertexList.get(i).getUserObject() == vertex0.getUserObject()) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (vertexList.get(j).getUserObject() == vertex1.getUserObject()) {
                        adjacencyMatrix[i][j] = edge;
                        return edge;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public <F> boolean removeVertex(Vertex<F> vertex) {
        boolean removed = false;
        int index = 0;
        List<Vertex<E>> vertexList = new ArrayList<>(vertices);
        for(int i = 0; i<vertexList.size(); i++){
            if(vertexList.get(i).getUserObject().equals(vertex.getUserObject())){
                index = i;
                removed = true;
                break;
            }
        }

        if(removed) {
            Edge<E>[][] currentAdjacency = this.adjacencyMatrix;
            Edge<E>[][] newAdjacencyMatrix = new Edge[vertices.size() - 1][vertices.size() - 1];

            //if vertex to remove is not at the last node
            if (index != vertices.size() - 1) {

                while (index < vertices.size() - 1) {

                    for (int i = 0; i < vertices.size(); i++) {
                        adjacencyMatrix[index][i] = adjacencyMatrix[index + 1][i];
                    }

                    for (int i = 0; i < vertices.size(); i++) {
                        adjacencyMatrix[i][index] = adjacencyMatrix[i][index + 1];
                    }
                    index++;
                }

                for (int i = 0; i < currentAdjacency.length - 1; i++) {
                    if (currentAdjacency[i].length - 1 >= 0)
                        System.arraycopy(currentAdjacency[i], 0, newAdjacencyMatrix[i], 0, currentAdjacency[i].length - 1);
                }

            //if deleted vertex is the last node
            } else {
                for (int i = 0; i < currentAdjacency.length - 1; i++) {
                    if (currentAdjacency[i].length - 1 >= 0) {
                        System.arraycopy(currentAdjacency[i], 0, newAdjacencyMatrix[i], 0, currentAdjacency[i].length - 1);
                    }
                }
            }

            vertices.remove(vertex);
            this.adjacencyMatrix = newAdjacencyMatrix;
        }

        return removed;
    }

    @Override
    public <F> boolean removeEdge(Edge<F> edge) {
        if(containsEdge(edge)){
            for(int i =0; i< adjacencyMatrix.length; i++){
                for(int j =0; j<adjacencyMatrix[i].length; j++){
                    if(adjacencyMatrix[i][j] != null && adjacencyMatrix[i][j].toString().contains(edge.toString())){
                        adjacencyMatrix[i][j] = null;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsVertex(Vertex<?> vertex) {
        Set<E> objectsOfVertices = vertices.stream().map(Vertex::getUserObject).collect(Collectors.toSet());
        return objectsOfVertices.contains(vertex.getUserObject());
    }

    @Override
    public boolean containsEdge(Edge<?> edge) {
        for(int i =0; i< adjacencyMatrix.length; i++){
            for(int j =0; j<adjacencyMatrix[i].length; j++){
                if(adjacencyMatrix[i][j] != null && adjacencyMatrix[i][j].toString().contains(edge.toString())){
                    return true;
                }
            }
        }
        return false;
    }

    public String displayAdjacencyMatrix()
    {
        System.out.print("Adjacency Matrix:");
        System.out.println();
        String row = "";

        for (int i = 0; i < adjacencyMatrix.length; ++i) {
            for (int j = 0; j < adjacencyMatrix[i].length; ++j) {
                row += String.format("%15s", adjacencyMatrix[i][j]);
            }
            row +='\n';
        }
        return row;
    }

    private class VertexImpl implements Vertex<E> {

        private E element;

        public VertexImpl(E element) {
            this.element = element;
        }

        @Override
        public E getUserObject() {
            return element;
        }

        @Override
        public void setUserObject(E element) {
            this.element = element;
        }

        @Override
        public Set<Edge<E>> incidentEdges() {
            Set<Edge<E>> edges = new HashSet<>();
            List<Vertex<E>> vertexList = new ArrayList<>(vertices);
            int index = vertexList.indexOf(this);
            for(int i = 0; i < adjacencyMatrix.length; i++){
                if(adjacencyMatrix[i][index] != null){
                    edges.add(adjacencyMatrix[i][index]);
                }
            }

            for(int i = 0; i < adjacencyMatrix.length; i++){
                if(adjacencyMatrix[index][i] != null){
                    edges.add(adjacencyMatrix[index][i]);
                }
            }
            return edges;
        }

        @Override
        public Set<Vertex<E>> adjacentVertices() {
            Set<Vertex<E>> adjacentVertices = new HashSet<>();
            Set<Edge<E>> edgeSet = edgeSet();
            for(Edge<E> e: edgeSet){
                Vertex<E> oppositeVertex = e.oppositeVertex(this);
                if(oppositeVertex != null){
                    adjacentVertices.add(oppositeVertex);
                }
            }
            return adjacentVertices;
        }

        @Override
        public boolean isAdjacent(Vertex<?> vertex) {
            return adjacentVertices().contains(vertex);
        }
    }

     private class EdgeImpl implements Edge<E> {
        private Vertex<E> vertex1, vertex2;

        public EdgeImpl(Vertex<E> vertex1, Vertex<E> vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }

        @Override
        public Vertex<E>[] endVertices() {
            return new Vertex[]{vertex1, vertex2};
        }

        @Override
        public Vertex<E> oppositeVertex(Vertex<E> vertex) {
            if (vertex1.equals(vertex))
                return vertex2;
            else
                return vertex1;
        }

        public String toString() {
            return "(" + vertex1.getUserObject() + "-" + vertex2.getUserObject() + ")";
        }
    }
}
