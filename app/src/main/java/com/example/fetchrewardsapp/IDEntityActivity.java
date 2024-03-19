package com.example.fetchrewardsapp;


public class IDEntityActivity {
    String id;
    String name;

    public IDEntityActivity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public IDEntityActivity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}