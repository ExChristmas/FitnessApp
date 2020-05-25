package com.example.fitnessapp.ui.journal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fitnessapp.model.dao.impl.ExerciseActionsGlobalDB;
import com.example.fitnessapp.model.entities.Exercise;

import java.util.List;

public class ListNotesNoteChangeSharedViewModel extends AndroidViewModel {

    ExerciseActionsGlobalDB exerciseActionsGlobalDB;

    public ListNotesNoteChangeSharedViewModel(@NonNull Application application) {
        super(application);
        exerciseActionsGlobalDB = new ExerciseActionsGlobalDB();
    }

    public LiveData<List<Exercise>> queryAllExercises() {
        return exerciseActionsGlobalDB.getAll();
    }

}
