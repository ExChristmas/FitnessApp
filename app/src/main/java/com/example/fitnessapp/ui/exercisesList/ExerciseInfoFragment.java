package com.example.fitnessapp.ui.exercisesList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.Exercise;

import java.lang.reflect.Field;

public class ExerciseInfoFragment extends Fragment {

    private Exercise exercise;
    private ActionBar actionBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.exercise_info_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView textViewName = view.findViewById(R.id.textViewNameExInfo);
        TextView textViewDescript = view.findViewById(R.id.textViewDescriptExInfo);
        ImageView image = view.findViewById(R.id.imageViewExInfo);

        textViewName.setText(exercise.getName());
        textViewDescript.setText(exercise.getDescription());
        image.setImageResource(getId(exercise.getId(), R.drawable.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ExercisesFragment exerciseFragment = new ExercisesFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, exerciseFragment);
        transaction.commit();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(false);
        return super.onOptionsItemSelected(item);
    }

    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}