package ua.aabrasha.edu.crudapplication.model;

/**
 * Created by Andrii Abramov on 7/5/16.
 */
public class Car {

    private long id;
    private String name;
    private int year;

    public Car() {
    }

    public Car(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                year == car.year &&
                com.google.common.base.Objects.equal(name, car.name);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(id, name, year);
    }
}
