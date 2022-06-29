package ex1_4;

import java.util.*;

public class RandomObtainableList<E> extends ArrayList<E> implements RandomObtainable<E>{

    private final Random random;

    public RandomObtainableList(){
        super();
        this.random =  new Random();
    }
    @Override
    public E getRandom() throws NoSuchElementException {
        if(super.size() <=0){
            throw new NoSuchElementException();
        }
        return super.get(random.nextInt(super.size()));
    }

    @Override
    public boolean removeRandom() throws UnsupportedOperationException {
        if(super.size()<=0){
            throw new UnsupportedOperationException();
        }
        super.remove(random.nextInt(super.size()));
        return true;
    }

    public static void main (String[]args){
        RandomObtainableList<String> list = new RandomObtainableList<>();
        System.out.println("Enter colors to the list");
        List<String> colors = Arrays.asList("Red", "Pink", "White", "Black", "Orange", "Brown", "Gray", "Purple");
        list.addAll(colors);

        System.out.println("Get a random color in the list");
        System.out.println(list.getRandom());
        list.removeRandom();
        System.out.println("New list after being removed a first random color");
        System.out.println(list);
        list.removeRandom();
        System.out.println("New list after being removed a second random color");
        System.out.println(list);

    }
}
