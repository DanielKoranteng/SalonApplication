package com.example.salonapplication;

public class Professional {
    private String name;
    private String rating;

    public Professional(String name, String rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }
}