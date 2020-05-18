package com.example.fitnessapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.fitnessapp.R;

public class ExercisesFragment extends Fragment {

    private ExercisesViewModel exercisesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exercisesViewModel =
                ViewModelProviders.of(this).get(ExercisesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_exercises, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        exercisesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}
