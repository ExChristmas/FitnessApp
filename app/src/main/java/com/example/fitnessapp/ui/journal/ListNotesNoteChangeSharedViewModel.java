package com.example.fitnessapp.ui.journal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fitnessapp.model.dao.impl.ExerciseActionsGlobalDB;
import com.example.fitnessapp.model.dao.impl.ExerciseActionsLocalDB;
import com.example.fitnessapp.model.dao.impl.NoteActionsLocalDB;
import com.example.fitnessapp.model.entities.Exercise;
import com.example.fitnessapp.model.entities.Note;

import java.util.List;

public class ListNotesNoteChangeSharedViewModel extends AndroidViewModel {

    ExerciseActionsGlobalDB exerciseGlobalDAO;
    ExerciseActionsLocalDB exerciseLocalDAO;
    public ListNotesNoteChangeSharedViewModel(@NonNull Application application) {
        super(application);
        exerciseGlobalDAO = new ExerciseActionsGlobalDB();
    }

    public LiveData<List<Exercise>> queryAllExercises() {
        return exerciseGlobalDAO.getAll();
    }

    public Exercise getExerciseById(String idExercise) {
        return exerciseLocalDAO.getById(idExercise);
    }

    public int getIndexExercise(List<Exercise> exercises, String idExercise) {
        for(int i = 0; i < exercises.size(); i++) {
            if (exercises.get(i).getId().equals(idExercise)) {
                return i;
            }
        }
        return -1;
    }

    public void putNotesOnDB(List<Note> notes) {
        NoteActionsLocalDB noteActionsLocalDB = new NoteActionsLocalDB(getApplication());
        for(Note note : notes) {
            noteActionsLocalDB.add(note);
        }
        noteActionsLocalDB.disconnect();
    }

    public void updateNote(Note note) {
        NoteActionsLocalDB noteActionsLocalDB = new NoteActionsLocalDB(getApplication());
        noteActionsLocalDB.update(note);
        noteActionsLocalDB.disconnect();
    }

}