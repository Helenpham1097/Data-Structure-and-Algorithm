package ex5_2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StudentInformation {
    private String id;
    private String name;
    private String address;
    private Date birthDay;

    public StudentInformation(String name, String address, Date birthDay) {
        Random random = new Random();
        int number = random.nextInt(99999);
        this.id = "AUT" + number;
        this.name = name;
        this.address = address;
        this.birthDay = birthDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return
                "id: " + id +
                        ", name: " + name +
                        ", address: " + address +
                        ", birthday: " + dateFormat.format(birthDay);
    }
}
