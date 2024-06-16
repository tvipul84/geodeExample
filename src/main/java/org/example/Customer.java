package org.example;

import lombok.Data;

import java.io.Serializable;

@Data
public class Customer implements Serializable {
    private CustomerKey key;
    private String firstName;
    private String lastName;
    private int age;

    public Customer(CustomerKey key, String firstName, String lastName, int age) {
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public CustomerKey getKey() {
        return key;
    }

    public void setKey(CustomerKey key) {
        this.key = key;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
