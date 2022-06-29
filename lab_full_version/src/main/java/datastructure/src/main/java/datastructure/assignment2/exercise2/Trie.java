package datastructure.assignment2.exercise2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Trie {

    private final TrieNode root;

    public static class TrieNode {
        private String word;
        private final Character character;
        private Map<Character, TrieNode> children = new HashMap<>();

        public void addChild(TrieNode node, Character character) {
            children.put(character, node);
        }

        public TrieNode(Character character, String word) {
            this.word = word;
            this.character = character;
        }

        public TrieNode() {
            this(null, null);
        }

        public Map<Character, TrieNode> getChildren() {
            return children;
        }

        public String getWord() {
            return word;
        }

        public boolean hasChildren() {
            return !children.isEmpty();
        }

        public void resetChildren() {
            children = new HashMap<>();
        }

        @Override
        public String toString() {
            String s = null;
            if (word != null) {
                s = String.format("%s -> %s", character, word);
            }

            if (s == null) {
                return String.format("%s -> {%s}", character, toChildrenString(TrieNode.this));
            }

            if (hasChildren()) {
                s += "," + String.format("%s -> {%s}", character, toChildrenString(TrieNode.this));
            }

            return s;
        }
    }

    private static String toChildrenString(TrieNode node) {

        return node.children
                .values()
                .stream()
                .map(TrieNode::toString)
                .collect(Collectors.joining(","));
    }

    public Trie() {
        root = new TrieNode();
    }

    public boolean add(String word) {

        if (word == null) {
            throw new IllegalArgumentException("Word is null");
        }

        if (contains(word)) {
            return false;
        }

        char[] chars = word.toCharArray();
        TrieNode currentNode = root;
        for (char c : chars) {
            currentNode = add(c, currentNode);
        }

        currentNode.word = word;

        return true;
    }

    private TrieNode add(Character character,
                         TrieNode node) {

        TrieNode childNode = node.children.get(character);
        if (childNode == null) {
            childNode = new TrieNode(character, null);
            node.addChild(childNode, character);
        }

        return childNode;
    }

    private TrieNode findNode(String word, Stack<TrieNode> path) {

        if (word == null) {
            throw new IllegalArgumentException("Word is null");
        }

        TrieNode currentNode = root;
        path.push(root);
        for (char c : word.toCharArray()) {
            currentNode = currentNode.getChildren().get(c);
            if (currentNode == null) {
                return null;
            } else {
                path.push(currentNode);
            }
        }
        return currentNode;
    }

    public boolean contains(String word) {
        TrieNode node = findNode(word, new Stack<>());
        return node != null && node.word != null && node.word.equals(word);
    }

    public boolean startWiths(String prefix) {
        TrieNode node = findNode(prefix, new Stack<>());
        return node != null;
    }

    public boolean remove(String word) {

        Stack<TrieNode> nodes = new Stack<>();
        TrieNode furthestNode = findNode(word, nodes);

        if (furthestNode == null) {
            return false;
        }

        while (!nodes.empty()) {
            TrieNode node = nodes.pop();

            if (node.hasChildren()) {
                if (node.word != null && node.word.equals(word)) {
                    node.word = null;
                    return true;
                }
            } else {
                TrieNode parentNode = nodes.peek();
                if (parentNode != null) {
                    parentNode.getChildren().remove(node.character);
                }
            }
        }
        return true;
    }

    public boolean removeAll(String prefix) {
        Stack<TrieNode> nodes = new Stack<>();
        TrieNode furthestNode = findNode(prefix, nodes);

        if (furthestNode == null) {
            return false;
        }

        furthestNode.resetChildren();

        while (!nodes.isEmpty()) {
            TrieNode node = nodes.pop();
            if (!node.hasChildren()) {
                if (!nodes.isEmpty()) {
                    TrieNode parent = nodes.peek();
                    if (parent != null) {
                        parent.getChildren().remove(node.character);
                    }
                }
            }
        }

        return true;
    }

    public Set<String> suggestions(String prefix) {
        TrieNode furthestNode = findNode(prefix, new Stack<>());
        if (furthestNode == null) {
            return Set.of();
        }

        Set<String> suggestions = new HashSet<>();
        addSuggestions(furthestNode, suggestions);

        return suggestions;
    }

    private void addSuggestions(TrieNode furthestNode,
                                Set<String> suggestions) {

        if (furthestNode != null && furthestNode.word != null) {
            suggestions.add(furthestNode.word);
        }

        if (furthestNode != null) {
            furthestNode.children.forEach((c, node) -> addSuggestions(node, suggestions));
        }
    }

    @Override
    public String toString() {
        return String.format("root -> {%s}", toChildrenString(root));
    }

    public TrieNode getRoot() {
        return root;
    }

    public static void main(String[] args) {
        Trie tree = new Trie();
        tree.add("the");
        tree.add("then");
        tree.add("there");
        tree.add("this");
        tree.add("that");

        tree.add("tea");
        tree.add("to");

        tree.add("hi");
        tree.add("him");
        tree.add("he");
        tree.add("her");
        tree.add("hit");

        tree.add("seth");
        tree.add("a");

        System.out.println(tree.suggestions("th"));
        System.out.println("...............");
        System.out.println(tree);
        tree.remove("the");
        System.out.println("...............");
        System.out.println("Remove word 'the':");
        System.out.println(tree);
        tree.removeAll("the");
        System.out.println("...............");
        System.out.println("Remove prefix 'the':");
        System.out.println(tree);
    }
}
