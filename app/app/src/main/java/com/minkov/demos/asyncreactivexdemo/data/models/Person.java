package com.minkov.demos.asyncreactivexdemo.data.models;

/**
 * Created by minkov on 10/15/17.
 */

public class Person {
    private String name;

    public Person(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
