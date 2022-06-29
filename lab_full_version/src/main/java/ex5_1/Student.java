
package ex5_1;

import java.util.List;
import java.util.Objects;

public class Student {
    private String name;


    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for(int i =0; i<this.name.length(); i++){
            int charValue = this.name.charAt(i);
            hash += charValue;
        }
        return hash;
    }
}
