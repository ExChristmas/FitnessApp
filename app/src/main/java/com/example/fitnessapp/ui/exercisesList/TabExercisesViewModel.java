package com.example.fitnessapp.ui.exercisesList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fitnessapp.model.dao.impl.ExerciseActionsGlobalDB;
import com.example.fitnessapp.model.dao.impl.ExerciseActionsLocalDB;
import com.example.fitnessapp.model.entities.Exercise;

import java.util.List;

public class TabExercisesViewModel extends AndroidViewModel {

    private ExerciseActionsLocalDB exerciseActionsLocalDB;
    private ExerciseActionsGlobalDB exerciseActionsGlobalDB;

    public TabExercisesViewModel(@NonNull Application application) {
        super(application);
        exerciseActionsLocalDB = new ExerciseActionsLocalDB(application);
        exerciseActionsGlobalDB = new ExerciseActionsGlobalDB();
    }

    public LiveData<List<Exercise>> queryExeption(String partOfBody) {
        LiveData<List<Exercise>> exeptionLiveData = exerciseActionsLocalDB.getByPartOfBody(partOfBody);
        if(exeptionLiveData == null) {
            return exerciseActionsGlobalDB.getByPartOfBody(partOfBody);
        }
        return exeptionLiveData;
    }

}