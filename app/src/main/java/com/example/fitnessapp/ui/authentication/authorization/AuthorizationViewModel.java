package com.example.fitnessapp.ui.authentication.authorization;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.model.Authentication;
import com.example.fitnessapp.model.dao.impl.NoteActionsLocalDB;
import com.example.fitnessapp.model.dao.impl.UserActionsLocalDB;
import com.example.fitnessapp.model.dao.impl.WorkoutActionsLocalDB;
import com.example.fitnessapp.model.entities.Note;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.entities.Workout;

import java.util.List;

public class AuthorizationViewModel extends AndroidViewModel {

    private Application application;
    private User user;
    private Authentication authentication;

    public AuthorizationViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        authentication = new Authentication(application.getApplicationContext());
    }

    public LiveData<User> autorization(String email, String password) {
        MutableLiveData<User> liveData = new MutableLiveData<>();
        authentication.authorization(email, password).observeForever(user -> {
            UserActionsLocalDB userActionsLocalDB = new UserActionsLocalDB(application);
            userActionsLocalDB.add(user);
            userActionsLocalDB.disconnect();
            this.user = user;
            liveData.setValue(user);
        });
        return liveData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void signOut() {
        user = null;
    }

    public LiveData<User> getUserSignedIn() {
        return authentication.checkSignIn();
    }

    public void setUserWorkouts(List<Workout> workouts) {
        this.user.setJournalWorkout(workouts);
    }

    public void setUserDeleteWorkout(Workout workout) {
        if(user != null && user.setJournalDeleteWorkout(workout.getId())) {
                WorkoutActionsLocalDB workoutDAO = new WorkoutActionsLocalDB(application);
                workoutDAO.remove(workout);
                workoutDAO.disconnect();
        }
    }

    public void setUserDeleteNote(Note note) {
        WorkoutActionsLocalDB workoutDAO = new WorkoutActionsLocalDB(application);
        if (!note.isEmpty()) {
            if (user.setJournalDeleteNote(note.getId(), note.getIdWorkout())) {
                NoteActionsLocalDB noteDAO = new NoteActionsLocalDB(application);
                noteDAO.remove(note);
                noteDAO.disconnect();
            }
        }
    }
}