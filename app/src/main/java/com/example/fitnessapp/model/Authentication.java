package com.example.fitnessapp.model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.internetconnection.InternetConnection;
import com.example.fitnessapp.model.dao.impl.UserActionsGlobalDB;
import com.example.fitnessapp.model.dao.impl.UserActionsLocalDB;
import com.example.fitnessapp.model.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {

    private FirebaseAuth mAuth;
    private UserActionsLocalDB userLocalDAO;
    private UserActionsGlobalDB userGlobalDAO;
    private Context context;

    public Authentication(Context context) {
        this.mAuth = FirebaseAuth.getInstance();
        this.userLocalDAO = new UserActionsLocalDB(context);
        this.userGlobalDAO = new UserActionsGlobalDB();
        this.context = context;
    }

    public MutableLiveData<User> authorization(String email, String password) {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        if(InternetConnection.isConnect(context)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            userGlobalDAO.getById(email).observeForever(userLiveData::setValue);
                        }
                    });
        } else {
            Toast.makeText(context, "Нет соединения с интернетом", Toast.LENGTH_SHORT).show();
        }
        return userLiveData;
    }

    public void registration(String name, String surname, String email, String password) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            User user = new User(email, name, surname);
                            userLocalDAO.add(user);
                            userGlobalDAO.add(user);
                        }
                    });
    }

    public void signOut() {
        mAuth.signOut();
    }

    public LiveData<User> checkSignIn() {
        if (InternetConnection.isConnect(context)) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                return userGlobalDAO.getByEmail(user.getEmail());
            }
            return new MutableLiveData<>();
        } else {
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            return new MutableLiveData<>();
        }
    }
}