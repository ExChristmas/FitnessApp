package com.example.fitnessapp.model.entities;

public class Exercise {

    private String id;
    private String name;
    private String description;

    public Exercise(String id, String nameExercise, String description) {
        this.id = id;
        this.name = nameExercise;
        this.description = description;
    }

    public Exercise(Exercise exercise) {
        this.id = exercise.id;
        this.name = exercise.name;
        this.description = exercise.description;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}