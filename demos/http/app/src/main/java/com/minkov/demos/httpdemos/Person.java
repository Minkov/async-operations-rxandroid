package com.minkov.demos.httpdemos;

/**
 * Created by minkov on 10/18/17.
 */

public class Person {
    private String name;
    private int age;

    public Person() {
        this("", -1);
    }

    public Person(String name, int age) {
        setName(name);
        setAge(age);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
