package datastructure.assignment2.exercise3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HashSetWithChainingTest {

    @Test
    public void contains_workPerfectly() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        chains.add("Simon");
        chains.add("Andy");

        assertThat(chains.contains("Andy")).isTrue();
        assertThat(chains.contains("Simon")).isTrue();
        assertThat(chains.contains("Jill")).isTrue();
        assertThat(chains.contains("Bob")).isTrue();
        assertThat(chains.contains("Bob1")).isFalse();
    }

    @Test
    public void containsAll_workPerfectly_ifNotExceedLoadFactor() {
        Set<String> chains = new HashSetWithChaining<>(20);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        chains.add("Simon");
        chains.add("Andy");

        System.out.println(chains);
        assertThat(chains.containsAll(Arrays.asList("Andy", "Simon", "Jill", "Bob"))).isTrue();
        assertThat(chains.containsAll(Arrays.asList("Andy", "Simon1"))).isFalse();
    }

    @Test
    public void containsAll_workPerfectly_ifExceedLoadFactor() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        chains.add("Simon");
        chains.add("Andy");

        System.out.println(chains);
        assertThat(chains.containsAll(Arrays.asList("Andy", "Simon", "Jill", "Bob"))).isTrue();
        assertThat(chains.containsAll(Arrays.asList("Andy", "Simon1"))).isFalse();
    }

    @Test
    public void getSize_workPerfectly_ifExceedLoadFactor() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        assertThat(chains.size()).isEqualTo(5);

        chains.add("Simon");
        chains.add("Andy");
        assertThat(chains.size()).isEqualTo(7);
    }

    @Test
    public void remove_workPerfectly_ifExceedLoadFactor() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        chains.add("Simon");
        chains.add("Andy");
        chains.add("Ann");


        System.out.println(chains);
        //Remove only node
        chains.remove("Bob");
        //Remove last node
        chains.remove("Simon");
        //Remove first node
        chains.remove("Nat");

        System.out.println("......................");
        System.out.println(chains);
        assertThat(chains.containsAll(Arrays.asList("Seth", "Amy", "Andy", "Jill", "Ann"))).isTrue();
        assertThat(chains.containsAll(Arrays.asList("Bob", "Simon", "Nat"))).isFalse();
        assertThat(chains.size()).isEqualTo(5);
    }

    @Test
    public void clear_workPerfectly_ifExceedLoadFactor() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        chains.add("Simon");
        chains.add("Andy");
        chains.add("Ann");

        chains.clear();

        assertThat(chains.isEmpty()).isTrue();


    }

    @Test
    public void iterator_throwsException_ifModifyConcurrently() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");

        Iterator<String> i = chains.iterator();

        chains.remove("Seth");

        assertThatThrownBy(() -> {
            while (i.hasNext()) {
                System.out.println(i.next());
            }
        }).isInstanceOf(ConcurrentModificationException.class);
    }

    @Test
    public void iterator_WorksPerfectly() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");

        System.out.println(chains);
        Iterator<String> i = chains.iterator();
        List<String> expected = new ArrayList<>();

        int count = 0;
        while (i.hasNext() && count++ < 3) {
            expected.add(i.next());
        }

        assertThat(expected).containsExactly("Amy", "Seth", "Jill");
        assertThat(chains.size()).isEqualTo(4);
    }

    @Test
    public void iterator_WorksPerfectly_whenRemove() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        chains.add("Simon");
        chains.add("Andy");

        System.out.println(chains);
        Iterator<String> i = chains.iterator();

        while (i.hasNext()) {
            if (Arrays.asList("Nat", "Jill", "Bob").contains(i.next())) {
                i.remove();
            }
        }

        System.out.println(chains);

        assertThat(Arrays.stream(chains.toArray()).collect(Collectors.toList()))
                .containsExactly("Amy", "Seth", "Simon", "Andy");
    }

    @Test
    public void toArray_WorksPerfectly() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        chains.add("Simon");
        chains.add("Andy");

        assertThat(Arrays.stream(chains.toArray()).collect(Collectors.toList()))
                .containsExactly("Amy", "Seth", "Simon", "Bob", "Andy", "Nat", "Jill");
    }

    @Test
    public void addDuplicated_doesNotContainsElementTwice() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        chains.add("Simon");
        chains.add("Andy");
        chains.add("Andy");
        chains.add("Nat");
        chains.add("Jill");

        assertThat(Arrays.stream(chains.toArray()).collect(Collectors.toList()))
                .containsExactly("Amy", "Seth", "Simon", "Bob", "Andy", "Nat", "Jill");

    }


    @Test
    public void toArrayGeneric_worksPerfectly_withSizeLessThanSet() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        chains.add("Simon");
        chains.add("Andy");

        assertThat(Arrays.stream(chains.toArray(new String[0])).collect(Collectors.toList()))
                .containsExactly("Amy", "Seth", "Simon", "Bob", "Andy", "Nat", "Jill");

    }

    @Test
    public void toArrayGeneric_worksPerfectly_withSizeLargerThanSet() {
        Set<String> chains = new HashSetWithChaining<>(6);
        chains.add("Seth");
        chains.add("Jill");
        chains.add("Bob");
        chains.add("Amy");
        chains.add("Nat");
        chains.add("Simon");
        chains.add("Andy");

        assertThat(Arrays.stream(chains.toArray(new String[8])).collect(Collectors.toList()))
                .containsExactly("Amy", "Seth", "Simon", "Bob", "Andy", "Nat", "Jill", null);

    }

    @Test
    public void equals_returnsTrue_withoutResizing() {
        Set<String> chains2 = new HashSetWithChaining<>(6);
        chains2.add("Seth");
        chains2.add("Jill");
        chains2.add("Bob");

        Set<String> chains1 = new HashSetWithChaining<>(6);
        chains1.add("Jill");
        chains1.add("Bob");
        chains1.add("Seth");

        assertThat(chains1.equals(chains2)).isTrue();
    }

    @Test
    public void equals_returnsTrue_withResizing() {
        Set<String> chains2 = new HashSetWithChaining<>(6);
        chains2.add("Seth");
        chains2.add("Jill");
        chains2.add("Bob");
        chains2.add("Amy");
        chains2.add("Nat");
        chains2.add("Simon");
        chains2.add("Andy");

        Set<String> chains1 = new HashSetWithChaining<>(6);
        chains1.add("Nat");
        chains1.add("Seth");
        chains1.add("Bob");
        chains1.add("Simon");
        chains1.add("Jill");
        chains1.add("Andy");
        chains1.add("Amy");

        assertThat(chains1.equals(chains2)).isTrue();
    }

    @Test
    public void equals_returnsFalse_withResizing() {
        Set<String> chains2 = new HashSetWithChaining<>(6);
        chains2.add("Seth");
        chains2.add("Jill");
        chains2.add("Bob");
        chains2.add("Amy");
        chains2.add("Nat");
        chains2.add("Simon");
        chains2.add("Andy");

        Set<String> chains1 = new HashSetWithChaining<>(6);
        chains1.add("Nat");
        chains1.add("Seth");
        chains1.add("Bob");
        chains1.add("Simon");
        chains1.add("Jill");
        chains1.add("Andy");
        chains1.add("Amy1");

        assertThat(chains1.equals(chains2)).isFalse();
    }

    @Test
    public void equals_returnsFalse_differentSizeSets() {
        Set<String> chains2 = new HashSetWithChaining<>(6);
        chains2.add("Seth");
        chains2.add("Jill");
        chains2.add("Bob");
        chains2.add("Amy");

        Set<String> chains1 = new HashSetWithChaining<>(6);
        chains1.add("Seth");
        chains1.add("Jill");
        chains1.add("Bob");

        assertThat(chains1.equals(chains2)).isFalse();
    }

    @Test
    public void retainAll_returnsTrue_whenTheSetChanges() {
        Set<String> chains2 = new HashSetWithChaining<>(6);
        chains2.add("Seth");
        chains2.add("Jill");
        chains2.add("Bob");
        chains2.add("Amy");
        chains2.add("Nat");
        chains2.add("Simon");
        chains2.add("Andy");

        List<String> retainElements = List.of("Amy", "Nat", "Andy", "StrangeName");
        assertThat(chains2.retainAll(retainElements)).isTrue();

        assertThat(Arrays.stream(chains2.toArray(new String[0])).collect(Collectors.toList()))
                .containsExactly("Amy", "Andy", "Nat");
    }

    @Test
    public void retainAll_returnsTrue_whenNoElementsIntersect() {
        Set<String> chains2 = new HashSetWithChaining<>(6);
        chains2.add("Seth");
        chains2.add("Jill");
        chains2.add("Bob");
        assertThat(chains2.retainAll(List.of("Hi", "Ha", "Go"))).isTrue();
        assertThat(Arrays.stream(chains2.toArray(new String[0])).collect(Collectors.toList())).isEmpty();
    }

    @Test
    public void retainAll_returnsFalse_whenNoElementsAreRemoved() {
        Set<String> chains2 = new HashSetWithChaining<>(6);
        chains2.add("Seth");
        chains2.add("Jill");
        chains2.add("Bob");
        System.out.println(chains2);
        assertThat(chains2.retainAll(List.of("Bob", "Jill", "Seth"))).isFalse();
        assertThat(Arrays.stream(chains2.toArray(new String[0])).collect(Collectors.toList()))
                .containsExactly("Seth", "Jill", "Bob");
    }
}
