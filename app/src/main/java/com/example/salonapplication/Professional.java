package com.example.salonapplication;

public class Professional {
    private String name;
    private String rating;
    private String specialization;
    private String experience;
    private String imageUrl;

    public Professional(String name, String rating, String specialization, String experience, String imageUrl) {
        this.name = name;
        this.rating = rating;
        this.specialization = specialization;
        this.experience = experience;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getName() { return name; }
    public String getRating() { return rating; }
    public String getSpecialization() { return specialization; }
    public String getExperience() { return experience; }
    public String getImageUrl() { return imageUrl; }
}