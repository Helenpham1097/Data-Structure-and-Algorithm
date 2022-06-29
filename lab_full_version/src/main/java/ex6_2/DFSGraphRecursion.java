package ex6_2;

import ex6_1.GraphADT;
import ex6_1.Vertex;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DFSGraphRecursion<E> {

    private Set<Vertex<E>> visitedVertices;

    public static void main(String[] args) {
        GraphADT<String> graph = new AdjacencyListGraph<>();
        Vertex<String> auckland = graph.addVertex("Auc");
        Vertex<String> wellington = graph.addVertex("Wel");
        Vertex<String> christchurch = graph.addVertex("Chr");
        Vertex<String> chatham = graph.addVertex("Cha");
        Vertex<String> fiji = graph.addVertex("Fij");
        Vertex<String> samoa = graph.addVertex("Sam");
        Vertex<String> tahiti = graph.addVertex("Tah");
        Vertex<String> brisbane = graph.addVertex("Bri");
        Vertex<String> sydney = graph.addVertex("Syd");
        Vertex<String> melbourne = graph.addVertex("Mel");
        graph.addEdge(auckland, wellington);
        graph.addEdge(auckland, christchurch);
        graph.addEdge(auckland, fiji);
        graph.addEdge(auckland, samoa);
        graph.addEdge(auckland, tahiti);
        graph.addEdge(auckland, brisbane);
        graph.addEdge(auckland, sydney);
        graph.addEdge(auckland, melbourne);
        graph.addEdge(wellington, christchurch);
        graph.addEdge(wellington, chatham);
        graph.addEdge(christchurch, melbourne);

        DFSGraphRecursion search = new DFSGraphRecursion<>();
        search.DFS(auckland);
        System.out.println(search.visitedVertices);
    }

    public void DFS(Vertex<E> startVertex) {
        this.visitedVertices = new HashSet<>();
        DFSWithRecursion(startVertex, visitedVertices);
    }

    private void DFSWithRecursion(Vertex<E> vertex, Set<Vertex<E>> vistedVertices) {
        vistedVertices.add(vertex);

        for (Vertex<E> v : vertex.adjacentVertices()) {
            if (!vistedVertices.contains(v)) {
                DFSWithRecursion(v, vistedVertices);
            }
        }
    }

    public void DSFWithoutRecursion(Vertex<E> startVertex) {
        Stack<Vertex<E>> stack = new Stack<>();
        this.visitedVertices = new HashSet<>();

        stack.push(startVertex);
        visitedVertices.add(startVertex);

        while (!stack.isEmpty()) {

            Vertex<E> currentVertex = stack.pop();

            if (!visitedVertices.contains(currentVertex)) {
                visitedVertices.add(currentVertex);

                for (Vertex<E> v : currentVertex.adjacentVertices()) {
                    if (!visitedVertices.contains(v)) {
                        stack.push(v);
                    }
                }
            }
        }
    }
}
