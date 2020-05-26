package com.example.fitnessapp.model.dao.impl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.model.entities.Exercise;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExerciseActionsGlobalDB {

    private FirebaseFirestore db;

    public ExerciseActionsGlobalDB() {
        db = FirebaseFirestore.getInstance();
    }

    public LiveData<List<Exercise>> getByPartOfBody(String partOfBody) {
        MutableLiveData<List<Exercise>> exerciseLiveData = new MutableLiveData();
        CollectionReference exerciseRef = db.collection("exercise");
        exerciseRef.whereEqualTo("partOfBody", partOfBody)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Exercise> exercises = new ArrayList<>();
                for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                    Exercise exercise = document.toObject(Exercise.class);
                    exercise.setId(document.getId());

                    exercises.add(exercise);
                }
                exerciseLiveData.setValue(exercises);
            }
        });

        return exerciseLiveData;
    }

    public LiveData<List<Exercise>> getAll() {
        MutableLiveData<List<Exercise>> exerciseLiveData = new MutableLiveData();
        db.collection("exercise")
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        List<Exercise> exercises = new ArrayList<>();
                        for (QueryDocumentSnapshot document :
                                Objects.requireNonNull(task.getResult())) {

                            Exercise exercise = document.toObject(Exercise.class);
                            exercise.setId(document.getId());

                            exercises.add(exercise);
                        }
                        exerciseLiveData.setValue(exercises);
                    }
                });

        return exerciseLiveData;
    }

}