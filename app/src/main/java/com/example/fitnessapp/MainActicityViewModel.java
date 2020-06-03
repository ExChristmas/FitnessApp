package com.example.fitnessapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fitnessapp.model.dao.impl.ExerciseActionsGlobalDB;
import com.example.fitnessapp.model.dao.impl.ExerciseActionsLocalDB;
import com.example.fitnessapp.model.dao.impl.NoteActionsLocalDB;
import com.example.fitnessapp.model.dao.impl.UserActionsLocalDB;
import com.example.fitnessapp.model.dao.impl.WorkoutActionsLocalDB;
import com.example.fitnessapp.model.entities.Exercise;
import com.example.fitnessapp.model.entities.Note;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.entities.Workout;
import com.example.fitnessapp.model.internetconnection.InternetConnection;

import java.util.List;

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
                List<Exercise> allExercises = exerciseLocalDAO.getAll();
                if(allExercises != null) {
                    for(Exercise exercise : allExercises) {
                        exerciseLocalDAO.remove(exercise);
                    }
                }
                for (Exercise exercise : exercises) {
                    exerciseLocalDAO.add(exercise);
                }
                exerciseLocalDAO.disconnect();
            });
        }
    }

    public void selectAll() {

        UserActionsLocalDB userActionsLocalDB = new UserActionsLocalDB(application);
        WorkoutActionsLocalDB workoutActionsLocalDB = new WorkoutActionsLocalDB(application);
        NoteActionsLocalDB noteActionsLocalDB = new NoteActionsLocalDB(application);
        ExerciseActionsLocalDB exerciseActionsLocalDB = new ExerciseActionsLocalDB(application);

        List<User> allUsers = userActionsLocalDB.getAll();
        if(allUsers != null) {
            for (User user : allUsers) {
                System.out.println(user.toString());
            }
        }

        System.out.println("=====================================================================");

        List<Workout> allWorkouts = workoutActionsLocalDB.getAll();
        if(allWorkouts != null) {
            for (Workout workout : allWorkouts) {
                System.out.println(workout.toString());
            }
        }

        System.out.println("=====================================================================");

        List<Note> allNotes = noteActionsLocalDB.getAll();
        if(allNotes != null) {
            for (Note note : allNotes) {
                System.out.println(note.toString());
            }
        }

        System.out.println("=====================================================================");

        List<Exercise> allExercises = exerciseActionsLocalDB.getAll();
        if(allExercises != null) {
            for (Exercise exercise : allExercises) {
                System.out.println(exercise.toString());
            }
        }
    }

}