package ex6_2;

import ex6_1.Edge;
import ex6_1.GraphADT;
import ex6_1.Vertex;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AdjacencyListGraph<E> implements GraphADT<E> {

    private Set<Vertex<E>> vertices;
    private Set<Edge<E>> edges;
    // map holding adjacency list (as a set) for each vertex
    private Map<Vertex<E>, Set<Edge<E>>> adjacencyLists;

    public AdjacencyListGraph() {
        this.vertices = new HashSet<Vertex<E>>();
        this.edges = new HashSet<Edge<E>>();
        this.adjacencyLists = new HashMap<Vertex<E>, Set<Edge<E>>>();
    }

    public <F extends E> AdjacencyListGraph(GraphADT<F> graph) {
        this();
        addGraph(graph);
    }

    // removes all vertices and edges from the graph
    public void clear() {
        vertices.clear();
        edges.clear();
        adjacencyLists.clear();
    }

    // returns true if the graph has no vertices nor edges
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    // returns a view of the vertices as a Set
    public Set<Vertex<E>> vertexSet() {
        return Collections.unmodifiableSet(vertices);
    }

    // returns a view of the edges as a Set
    public Set<Edge<E>> edgeSet() {
        return Collections.unmodifiableSet(edges);
    }

    // generic method which adds a copy of the graph (that has elements
    // of type F which extends type E) into this graph
    public <F extends E> void addGraph(GraphADT<F> graph) {  // add all the vertices to this graph and keep track of the
        // correspondence between old vertices in specified graph
        // and new vertices added to this graph
        HashMap<Vertex<F>, Vertex<E>> correspondence
                = new HashMap<Vertex<F>, Vertex<E>>();
        Set<Vertex<F>> oldVertices = graph.vertexSet();
        for (Vertex<F> oldVertex : oldVertices) {
            Vertex<E> newVertex = addVertex(oldVertex.getUserObject());
            correspondence.put(oldVertex, newVertex);
        }
        // add all the edges to this graph
        Set<Edge<F>> oldEdges = graph.edgeSet();
        for (Edge<F> oldEdge : oldEdges) {  // obtain the one or two end vertices for the old edge
            Vertex<F>[] oldEndVertices = oldEdge.endVertices();
            // add an appropriate new edge in this graph
            addEdge(correspondence.get(oldEndVertices[0]),
                    correspondence.get(oldEndVertices[1]));
        }
    }

    // adds and returns a new isolated vertex to the graph
    public Vertex<E> addVertex(E element) {
        AdjacencyListVertex vertex = new AdjacencyListVertex(element);
        addVertex(vertex);
        return vertex;
    }

    // helper method to add a Vertex object to the graph
    private void addVertex(Vertex<E> vertex) {
        vertices.add(vertex);
        adjacencyLists.put(vertex, new HashSet<Edge<E>>());
    }

    // adds and returns a new undirected edge between two vertices
    // Note: if the end vertices are not already in the graph
    // then copies of them are added as well
    public Edge<E> addEdge(Vertex<E> vertex0, Vertex<E> vertex1) {  // first add the end vertices if not already in graph
        if (!containsVertex(vertex0)) {
            addVertex(vertex0);
        }
        if (!containsVertex(vertex1)) {
            addVertex(vertex1);
        }
        // create the new edge
        Edge<E> edge = new AdjacencyListEdge(vertex0, vertex1);
        edges.add(edge);
        // update the adjacency list for each end vertex
        adjacencyLists.get(vertex0).add(edge);
        adjacencyLists.get(vertex1).add(edge);
        return edge;
    }

    // removes the specified vertex from the graph
    public <F> boolean removeVertex(Vertex<F> vertex) {
        if (!containsVertex(vertex)) {
            return false;
        } else {  // first remove all incident edges
            Set<Edge<F>> edgesToRemove = vertex.incidentEdges();
            Iterator<Edge<F>> edgeIterator = edgesToRemove.iterator();
            while (edgeIterator.hasNext()) {
                Edge<F> edgeToRemove = edgeIterator.next();
                edgeIterator.remove(); // uses iterator to remove edge
                // remove edge from adjacency lists of both its end vertices
                Vertex<F>[] endVertices = edgeToRemove.endVertices();
                adjacencyLists.get(endVertices[0]).remove(edgeToRemove);
                adjacencyLists.get(endVertices[1]).remove(edgeToRemove);
            }
            // remove the vertex
            vertices.remove(vertex);
            return true;
        }
    }

    // removes the specified undirected edge from the graph
    public <F> boolean removeEdge(Edge<F> edge) {
        if (!containsEdge(edge)) {
            return false;
        } else {
            edges.remove(edge);
            // remove edge from adjacency lists of both end vertices
            Vertex<F>[] endVertices = edge.endVertices();
            adjacencyLists.get(endVertices[0]).remove(edge);
            adjacencyLists.get(endVertices[1]).remove(edge);
            return true;
        }
    }

    // returns whether the specified vertex is in the graph
    public boolean containsVertex(Vertex<?> vertex) {
        return vertices.contains(vertex);
    }

    // returns whether the specified edge is in the graph
    public boolean containsEdge(Edge<?> edge) {
        return edges.contains(edge);
    }

    public String toString() {
        String output = "Graph:\n";
        for (Vertex<E> vertex : vertices) {
            output += "" + vertex + " has edges:"
                    + adjacencyLists.get(vertex) + "\n";
        }
        return output;
    }

    // inner class that implements a vertex for the AdjacencyListGraph
    private class AdjacencyListVertex implements Vertex<E> {

        private E element;

        public AdjacencyListVertex(E element) {
            this.element = element;
        }

        // returns the element held in the vertex
        public E getUserObject() {
            return element;
        }

        // sets the element held in the vertex
        public void setUserObject(E element) {
            this.element = element;
        }

        // returns the edges connecting with this vertex as a Set
        public Set<Edge<E>> incidentEdges() {
            return adjacencyLists.get(this);
        }

        // returns vertices that are adjacent to this vertex as a Set
        public Set<Vertex<E>> adjacentVertices() {
            Set<Edge<E>> adjacencyList = adjacencyLists.get(this);
            Set<Vertex<E>> vertices = new HashSet<Vertex<E>>();
            for (Edge<E> edge : adjacencyList) {
                vertices.add(edge.oppositeVertex(this));
            }
            return vertices;
        }

        // returns whether specified vertex is adjacent to this vertex
        public boolean isAdjacent(Vertex<?> vertex) {
            return adjacentVertices().contains(vertex);
        }

        // overridden method which returns a hash code for this vertex
        public int hashCode() {
            if (element == null) {
                return 0;
            } else {
                return element.hashCode();
            }
        }

        public String toString() {
            return "" + element;
        }
    }

    // inner class that implements a vertex for the AdjacencyListGraph
    private class AdjacencyListEdge implements Edge<E> {

        private Vertex<E> vertex1, vertex2;

        public AdjacencyListEdge(Vertex<E> vertex1, Vertex<E> vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }

        // returns the two end vertices for this edge as an array
        public Vertex<E>[] endVertices() {
            Vertex<E>[] vertices = (Vertex<E>[]) (new Vertex[2]);//unchecked
            vertices[0] = vertex1;
            vertices[1] = vertex2;
            return vertices;
        }

        // returns the end vertex opposite the specified vertex
        public Vertex<E> oppositeVertex(Vertex<E> vertex) {
            if (vertex1.equals(vertex)) {
                return vertex2;
            } else {
                return vertex1;
            }
        }

        public String toString() {
            return "(" + vertex1 + "-" + vertex2 + ")";
        }
    }
}
