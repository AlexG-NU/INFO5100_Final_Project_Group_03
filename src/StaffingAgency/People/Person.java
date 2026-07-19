/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffingAgency.People;

/**
 *
 * @author abhit
 */

import java.util.regex.Pattern;

public abstract class Person {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private final int personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String skills;

    protected Person(int personId, String firstName, String lastName, String email, String phone, String skills) {
        this.personId = personId;
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        this.phone = phone;
        this.skills = (skills == null) ? "" : skills;
    }

    public int getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName cannot be blank");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName cannot be blank");
        }
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSkills() {
        return skills;
    }

    protected void setSkills(String skills) {
        this.skills = (skills == null) ? "" : skills;
    }
}
    

