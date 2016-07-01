package ua.aabrasha.edu.crimeapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ua.aabrasha.edu.crimeapp.model.Person.Gender.FEMALE;
import static ua.aabrasha.edu.crimeapp.model.Person.Gender.MALE;

/**
 * Created by Andrii Abramov on 7/1/16.
 */
public class PeopleContainer {

    private static List<Person> people;

    static {
        people = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            Person person = new Person();
            person.setAge((int) Math.ceil(Math.random() * 100));
            person.setGender(Math.random() > 0.5 ? FEMALE : MALE);
            person.setName("Person #" + i);
            people.add(person);
        }
    }

    public static List<Person> getPeople() {
        return people;
    }

    public static Person getWithUUID(UUID id) {
        for (Person person : people) {
            if (person.getId().equals(id))
                return person;
        }
        return null;
    }
}
