package com.example.fitnessapp.ui.exercisesList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fitnessapp.model.dao.impl.ExerciseActionsGlobalDB;
import com.example.fitnessapp.model.dao.impl.ExerciseActionsLocalDB;
import com.example.fitnessapp.model.entities.Exercise;
import com.example.fitnessapp.model.internetconnection.InternetConnection;

import java.util.List;

public class TabExercisesViewModel extends AndroidViewModel {

    private ExerciseActionsLocalDB exerciseActionsLocalDB;
    private ExerciseActionsGlobalDB exerciseActionsGlobalDB;
    private Application application;

    public TabExercisesViewModel(@NonNull Application application) {
        super(application);
        exerciseActionsLocalDB = new ExerciseActionsLocalDB(application);
        exerciseActionsGlobalDB = new ExerciseActionsGlobalDB();
        this.application = application;
    }

    public LiveData<List<Exercise>> queryExeption(String partOfBody) {

        if(InternetConnection.isConnect(application)) {
            return exerciseActionsGlobalDB.getByPartOfBody(partOfBody);
        }
        return exerciseActionsLocalDB.getByPartOfBody(partOfBody);

    }

}