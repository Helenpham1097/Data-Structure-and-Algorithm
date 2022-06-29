package datastructure.assignment2.exercise2;

import datastructure.assignment2.exercise2.Trie.TrieNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrieTest {

    @Test
    public void addMultipleWords_shouldHaveProperNodeStructure() {
        Trie tree = new Trie();
        tree.add("tea");
        tree.add("to");
        tree.add("two");

        assertThat(tree.getRoot().getChildren()).containsOnlyKeys('t');
        assertThat(tree.getRoot().getChildren()).hasSize(1);

        // assert 't' node
        TrieNode tNode = tree.getRoot().getChildren().get('t');
        assertThat(tNode.getChildren()).containsOnlyKeys('e', 'o', 'w');
        assertThat(tNode.getChildren().get('o').getWord()).isEqualTo("to");

        //asser 'tea'
        TrieNode eNode = tNode.getChildren().get('e');
        assertThat(eNode.getChildren()).containsOnlyKeys('a');
        assertThat(eNode.getChildren().get('a').getWord()).isEqualTo("tea");

        //assert 'two'
        TrieNode wNode = tNode.getChildren().get('w');
        assertThat(wNode.getChildren()).containsOnlyKeys('o');
        assertThat(wNode.getChildren().get('o').getWord()).isEqualTo("two");
    }

    @Test
    public void addDuplicateWords_shouldHaveProperNodeStructure() {
        Trie tree = new Trie();
        tree.add("then");

        assertThat(tree.add("there")).isTrue();
        assertThat(tree.add("the")).isTrue();
        assertThat(tree.add("then")).isFalse();

        System.out.println(tree);
    }

    @Test
    public void containsWords_shouldWorkProperly() {
        Trie tree = new Trie();
        tree.add("tea");
        tree.add("to");
        tree.add("two");

        assertThat(tree.contains("tea")).isTrue();
        assertThat(tree.contains("to")).isTrue();
        assertThat(tree.contains("two")).isTrue();
        assertThat(tree.contains("tho")).isFalse();
        assertThat(tree.contains("wo")).isFalse();
        assertThat(tree.contains("a")).isFalse();
    }

    @Test
    public void startWithPrefix_shouldWorkProperly() {
        Trie tree = new Trie();
        tree.add("tea");
        tree.add("to");
        tree.add("two");
        tree.add("tee");
        tree.add("too");

        assertThat(tree.startWiths("tea")).isTrue();
        assertThat(tree.startWiths("te")).isTrue();
        assertThat(tree.startWiths("tw")).isTrue();
        assertThat(tree.startWiths("to")).isTrue();
        assertThat(tree.startWiths("ta")).isFalse();
    }

    @Test
    public void removeWords_shouldWorkProperly() {
        Trie tree = new Trie();
        tree.add("the");
        tree.add("then");
        tree.add("there");
        tree.add("a");
        tree.add("to");
        tree.add("hi");
        tree.add("her");

        assertThat(tree.remove("tree")).isFalse();
        assertThat(tree.remove("three")).isFalse();

        System.out.println(tree);
        assertThat(tree.remove("the")).isTrue();
        System.out.println(tree);
        assertThat(tree.startWiths("the")).isTrue();
        assertThat(tree.contains("the")).isFalse();
        assertThat(tree.contains("then")).isTrue();
        assertThat(tree.contains("there")).isTrue();

        assertThat(tree.remove("hi")).isTrue();
        assertThat(tree.contains("hi")).isFalse();
        assertThat(tree.startWiths("hi")).isFalse();
        assertThat(tree.contains("her")).isTrue();
    }

    @Test
    public void removePrefix_shouldWorkProperly() {
        Trie tree = new Trie();
        tree.add("the");
        tree.add("then");
        tree.add("there");
        tree.add("this");
        tree.add("that");

        tree.add("him");
        tree.add("his");

        assertThat(tree.removeAll("though")).isFalse();

        assertThat(tree.removeAll("the")).isTrue();
        System.out.println(tree);
        assertThat(tree.startWiths("the")).isFalse();
        assertThat(tree.contains("this")).isTrue();
        assertThat(tree.contains("that")).isTrue();

        assertThat(tree.removeAll("th")).isTrue();
        assertThat(tree.getRoot().getChildren()).hasSize(1);
    }

    @Test
    public void suggestions_shouldWorkProperly() {
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

        assertThat(tree.suggestions("th")).containsExactlyInAnyOrder("the", "then", "there", "this", "that");
        assertThat(tree.suggestions("hi")).containsExactlyInAnyOrder("hi", "him", "hit");
    }
}