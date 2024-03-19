package com.example.fetchrewardsapp;

// Represents an entity with an ID and a name
public class IDEntityActivity {
    String id;
    String name;

    // Constructor with parameters to initialize ID and name
    public IDEntityActivity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Default constructor
    public IDEntityActivity() {
    }

    // Getter method for ID
    public String getId() {
        return id;
    }

    // Setter method for ID
    public void setId(String id) {
        this.id = id;
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Setter method for name
    public void setName(String name) {
        this.name = name;
    }
}
