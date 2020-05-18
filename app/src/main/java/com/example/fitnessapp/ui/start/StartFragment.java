package com.example.fitnessapp.ui.start;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.fitnessapp.R;

public class StartFragment extends Fragment {

    private StartViewModel startViewModel;
    private TextView textInfo;
    private EditText editTextEml;
    private EditText editTextPas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        startViewModel =
                ViewModelProviders.of(getActivity()).get(StartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_exercises, container, false);

        this.textInfo = root.findViewById(R.id.textView);
//        this.editTextEml = root.findViewById(R.id.editTextEml);
//        this.editTextPas = root.findViewById(R.id.editTextPas);

        oserveOnTextInfo();
        observeOnEditTextEml();
        observeOnEditTextPas();

        return root;
    }

    private void oserveOnTextInfo() {
        startViewModel.getmText().observe(getViewLifecycleOwner(), s -> {
            textInfo.setText(s);
        });
    }

    private void observeOnEditTextEml() {
        startViewModel.getmTextEml().observe(getViewLifecycleOwner(), s -> {
            editTextEml.setText(s);
        });
    }

    private void observeOnEditTextPas() {
        startViewModel.getmTextPas().observe(getViewLifecycleOwner(), s -> {
            editTextPas.setText(s);
        });
    }
}