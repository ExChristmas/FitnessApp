package com.example.fitnessapp.ui.authentication.registration;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.ui.authentication.authorization.AuthorizationFragment;
import com.example.fitnessapp.ui.authentication.authorization.AuthorizationViewModel;

public class RegistrationFragment extends Fragment {

    private AuthorizationViewModel authorizationViewModel;
    private RegistrationViewModel registrationViewModel;
    private ActionBar actionBar;
    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextEmail;
    private EditText editTextPas;
    private EditText editTextConfPas;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.authorizationViewModel =
                new ViewModelProvider(getActivity()).get(AuthorizationViewModel.class);
        this.registrationViewModel =
                new ViewModelProvider(getActivity()).get(RegistrationViewModel.class);
        return inflater.inflate(R.layout.registration_fragment, container,
                false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.editTextName = view.findViewById(R.id.editTextRegName);
        this.editTextSurname = view.findViewById(R.id.editTextRegSurname);
        this.editTextEmail = view.findViewById(R.id.editTextRegEmail);
        this.editTextPas = view.findViewById(R.id.editTextRegPassword);
        this.editTextConfPas = view.findViewById(R.id.editTextRegConfPass);
        Button buttonReg = view.findViewById(R.id.buttonRegInReg);

        this.editTextName.setText("Alex");
        this.editTextSurname.setText("Ivanov");
        this.editTextEmail.setText("ivanov@mail.ru");
        this.editTextPas.setText("projectApp");
        this.editTextConfPas.setText("projectApp");

        buttonReg.setOnClickListener(v -> {
            registrationViewModel.registration(editTextName.getText().toString(),
                    editTextSurname.getText().toString(), editTextEmail.getText().toString(),
                    editTextPas.getText().toString(), editTextConfPas.getText().toString());
            AuthorizationFragment authorizationFragment = new AuthorizationFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, authorizationFragment);
            transaction.commit();
        });

        actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AuthorizationFragment authorizationFragment = new AuthorizationFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, authorizationFragment);
        transaction.commit();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(false);
        return super.onOptionsItemSelected(item);
    }
}