package com.example.fitnessapp.model.entities;

public class Exercise {

    private String id;
    private String name;
    private String partOfBody;
    private String description;

    public Exercise(){
    }

    public Exercise(String id, String nameExercise, String partOfBody, String description) {
        this.id = id;
        this.name = nameExercise;
        this.partOfBody = partOfBody;
        this.description = description;
    }

    public Exercise(Exercise exercise) {
        this.id = exercise.id;
        this.name = exercise.name;
        this.partOfBody = exercise.partOfBody;
        this.description = exercise.description;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getPartOfBody() {
        return this.partOfBody;
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