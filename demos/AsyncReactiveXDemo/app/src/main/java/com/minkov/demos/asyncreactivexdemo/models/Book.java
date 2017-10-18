package com.minkov.demos.asyncreactivexdemo.models;

/**
 * Created by minkov on 10/18/17.
 */

public class Book {
    private String title;
    private String description;
    private String id;

    public Book(String id, String title, String description) {
        setId(id);
        setTitle(title);
        setDescription(description);
    }

    public Book(String title, String description) {
        this("", title, description);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Book && equals((Book) obj);
    }

    public boolean equals(Book other) {
        return getTitle().equals(other.getTitle())
                && getDescription().equals(other.getDescription())
                && getId().equals(other.getId());
    }
}
