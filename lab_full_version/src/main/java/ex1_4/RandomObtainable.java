package ex1_4;

import java.util.NoSuchElementException;

public interface RandomObtainable<E>{
    public E getRandom() throws NoSuchElementException;
    public boolean removeRandom() throws UnsupportedOperationException;
}
