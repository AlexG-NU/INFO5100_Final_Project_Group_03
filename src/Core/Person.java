/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Alex
 */
public class Person {
    
    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(1000);
    private final int personId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String zipCode;

    public Person(String name) {
        this.personId = ID_SEQUENCE.incrementAndGet();
        setName(name);
    }
    
    public Person(String firstName, String lastName) {
        this.personId = ID_SEQUENCE.incrementAndGet();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getPersonId() {
        return personId;
    }
    
    public String getName() {
        if (lastName == null || lastName.isEmpty()) {
            return firstName == null ? "" : firstName;
        }
        return (firstName == null || firstName.isEmpty() ? "" : firstName + " ") + lastName;
    }

    public void setName(String name) {
        if (name == null) {
            this.firstName = null;
            this.lastName = null;
            return;
        }
        String trimmed = name.trim();
        int lastSpace = trimmed.lastIndexOf(' ');
        if (lastSpace == -1) {
            this.firstName = trimmed;
            this.lastName = "";
        } else {
            this.firstName = trimmed.substring(0, lastSpace);
            this.lastName = trimmed.substring(lastSpace + 1);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
