package com.example.fitnessapp.model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.fitnessapp.model.dao.impl.UserActionsGlobalDB;
import com.example.fitnessapp.model.dao.impl.UserActionsLocalDB;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.entities.Workout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Authentication {

    private FirebaseAuth mAuth;
    private UserActionsLocalDB userLocalDAO;
    private UserActionsGlobalDB userGlobalDAO;

    public Authentication(Context context) {
        this.mAuth = FirebaseAuth.getInstance();
        this.userLocalDAO = new UserActionsLocalDB(context);
        this.userGlobalDAO = new UserActionsGlobalDB();
    }

    public MutableLiveData<User> authorization(String email, String password) {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userLiveData.setValue(userLocalDAO.getByEmail(email));
                        if (userLiveData.getValue() == null) {
                            userGlobalDAO.getById(email).observeForever(new Observer<User>() {
                                @Override
                                public void onChanged(User user) {
                                    userLiveData.setValue(user);
                                }
                            });
                        }
                    }
                });
        System.out.println("!!!!!!!!!!");
        return userLiveData;
    }

    public void registration(String name, String surname, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                        FirebaseUser userInFB = mAuth.getCurrentUser();
                        List<Workout> workouts = new ArrayList<>();
                        User user = new User(email, name, surname, email);
                        userLocalDAO.add(user);
                        userGlobalDAO.add(user);
                    }
                });

    }

    public void signOut() {
        mAuth.signOut();
    }

}