/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class PersonDirectory {
    private List<Person> personList;

    public PersonDirectory() {
        this.personList = new ArrayList<>();
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public Person createPerson(String name) {
        Person person = new Person(name);
        personList.add(person);
        return person;
    }
    
    public Person createPerson(String firstName, String lastName) {
        Person person = new Person(firstName, lastName);
        personList.add(person);
        return person;
    }

    public Person findPerson(String name) {
        for (Person p : personList) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
    
    public Person findPersonById(int personId) {
        for (Person p : personList) {
            if (p.getPersonId() == personId) {
                return p;
            }
        }
        return null;
    }
}
