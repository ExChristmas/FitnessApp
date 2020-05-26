package com.example.fitnessapp.ui.journal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fitnessapp.model.dao.impl.WorkoutActionsLocalDB;
import com.example.fitnessapp.model.entities.Workout;

public class JournalViewModel extends AndroidViewModel {

    public WorkoutActionsLocalDB workoutLocalDAO;

    public JournalViewModel(@NonNull Application application) {
        super(application);
        workoutLocalDAO = new WorkoutActionsLocalDB(application);
    }

    public void putInDB(Workout workout) {
        workoutLocalDAO.add(workout);
    }

}