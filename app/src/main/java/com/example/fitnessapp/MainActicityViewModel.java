package com.example.fitnessapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fitnessapp.model.dao.impl.ExerciseActionsGlobalDB;
import com.example.fitnessapp.model.dao.impl.ExerciseActionsLocalDB;
import com.example.fitnessapp.model.entities.Exercise;
import com.example.fitnessapp.model.internetconnection.InternetConnection;

public class MainActicityViewModel extends AndroidViewModel {

    private ExerciseActionsGlobalDB exerciseGlobalDAO;
    private ExerciseActionsLocalDB exerciseLocalDAO;
    private Application application;

    public MainActicityViewModel(@NonNull Application application) {
        super(application);
        exerciseGlobalDAO = new ExerciseActionsGlobalDB();
        exerciseLocalDAO = new ExerciseActionsLocalDB(application);
        this.application = application;
    }

    public void exerciseLoading() {
        if(InternetConnection.isConnect(application)) {
            exerciseGlobalDAO.getAll().observeForever(exercises -> {
                for(Exercise exercise : exercises) {
                    exerciseLocalDAO.add(exercise);
                }
            });
        }
    }

}
