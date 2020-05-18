package com.example.fitnessapp.ui.authentication.registration;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.fitnessapp.R;
import com.example.fitnessapp.ui.authentication.authorization.AuthorizationFragment;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel registrationViewModel;
    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextEmail;
    private EditText editTextPas;
    private EditText editTextConfPas;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.registrationViewModel =
                ViewModelProviders.of(getActivity()).get(RegistrationViewModel.class);
        View root = inflater.inflate(R.layout.registration_fragment, container,
                false);

        this.editTextName = root.findViewById(R.id.editTextRegName);
        this.editTextSurname = root.findViewById(R.id.editTextRegSurname);
        this.editTextEmail = root.findViewById(R.id.editTextRegEmail);
        this.editTextPas = root.findViewById(R.id.editTextRegPassword);
        this.editTextConfPas = root.findViewById(R.id.editTextRegConfPass);
        Button buttonReg = root.findViewById(R.id.buttonRegInReg);

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

        return root;
    }

}