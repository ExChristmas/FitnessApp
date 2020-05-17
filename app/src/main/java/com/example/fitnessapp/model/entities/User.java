package com.example.fitnessapp.model.entities;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String name;
    private String surname;
    private String email;

    private List<Workout> journal;

    public User() { }

    public User(String id, String name, String surname,
                String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.journal = new ArrayList<>();
    }

    public User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.surname = user.surname;
        this.email = user.email;
        this.journal = user.journal;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getEmail() {
        return this.email;
    }

    public List<Workout> getJournal() {
        return this.journal;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJournalWorkout(List<Workout> journal) {
        this.journal = journal;
    }

}