package com.example.fitnessapp.model.entities;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String surname;
    private String email;

    private List<Workout> journal;

    public User() { }

    public User(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.journal = new ArrayList<>();
    }

    public User(User user) {
        this.email = user.email;
        this.name = user.name;
        this.surname = user.surname;
        this.journal = user.journal;
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