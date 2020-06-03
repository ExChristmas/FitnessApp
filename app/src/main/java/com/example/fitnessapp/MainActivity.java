package com.example.fitnessapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fitnessapp.ui.authentication.account.AccountViewModel;
import com.example.fitnessapp.ui.authentication.authorization.AuthorizationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MainActicityViewModel mainActicityViewModel;
    private AccountViewModel accountViewModel;
    private AuthorizationViewModel authorizationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getApplicationContext().deleteDatabase("fitnessDB");
//
//        SettingsActionsLocalDB settingsActionsLocalDB =
//                new SettingsActionsLocalDB(getApplicationContext());
//        Settings settings = new Settings("typaev@mail.ru", 0);
//        settingsActionsLocalDB.update(settings);
//
//        Settings settings = settingsActionsLocalDB.getRecord();
//
//        System.out.println(settings.toString());

        mainActicityViewModel = new ViewModelProvider(this)
                .get(MainActicityViewModel.class);
        accountViewModel = new ViewModelProvider(this)
                .get(AccountViewModel.class);
        authorizationViewModel = new ViewModelProvider(this)
                .get(AuthorizationViewModel.class);

        mainActicityViewModel.selectAll();

        // загружаем упражнения в локальную базу
        mainActicityViewModel.exerciseLoading();

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,
                R.id.navigation_authentication)
                .build();

        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController,
                appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        accountViewModel.recording(authorizationViewModel.getUser());
    }
}