package ua.aabrasha.edu.crimeapp.model;

import java.util.UUID;

/**
 * Created by Andrii Abramov on 7/1/16.
 */
public class Person {

    private UUID id;
    private String name;
    private int age;
    private Gender gender;

    public Person() {
        id = UUID.randomUUID();
    }

    public Person(String name, int age, Gender gender) {
        this();
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public enum Gender {
        MALE, FEMALE
    }
}
