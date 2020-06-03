package com.example.fitnessapp.ui.exercisesList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.Exercise;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TabExercisesFragment extends Fragment {

    private int indexFragment;
    private List<Exercise> exercisesList;
    private MutableLiveData<Exercise> exerciseLiveData;

    TabExercisesFragment(int indexFragment, MutableLiveData<Exercise> exerciseLiveData) {
        this.indexFragment = indexFragment;
        this.exerciseLiveData = exerciseLiveData;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        TabExercisesViewModel tabExercisesViewModel = new ViewModelProvider(getActivity())
                .get(TabExercisesViewModel.class);

        View root = null;

        if (indexFragment == 0) {
            root = inflater.inflate(R.layout.arm_exercises_fragment,
                    container, false);
        } else if (indexFragment == 1) {
            root = inflater.inflate(R.layout.body_exercises_fragment,
                    container, false);
        } else if (indexFragment == 2) {
            root = inflater.inflate(R.layout.leg_exercises_fragment,
                    container, false);
        }

        if(root != null) {

            ListView listView = root.findViewById(R.id.list_view_tab_layout);

            LiveData<List<Exercise>> listExerciseLiveData = new MutableLiveData<>();
            if (indexFragment == 0) {
                listExerciseLiveData = tabExercisesViewModel.queryExeption("руки");
            } else if (indexFragment == 1) {
                listExerciseLiveData = tabExercisesViewModel.queryExeption("туловище");
            } else if (indexFragment == 2) {
                listExerciseLiveData = tabExercisesViewModel.queryExeption("ноги");
            }

                listExerciseLiveData.observe(getViewLifecycleOwner(), exercises -> {
                        exercisesList = exercises;
                    List<Integer> images = new ArrayList<>();

                    for (Exercise exercise : exercisesList) {
                        images.add(getId(exercise.getId(), R.drawable.class));
                    }

                    Adapter adapter = new Adapter(getActivity(), exercisesList, images);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        exerciseLiveData.setValue(exercises.get(position));
                    });
                });
        }

        return root;
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

    class Adapter extends ArrayAdapter<Exercise> {

        Context context;
        List<Exercise> exercises;
        List<Integer> images;

        Adapter(Context context, List<Exercise> exercises, List<Integer> images) {
            super(context, R.layout.element_list_exercises, R.id.editTextEmail, exercises);
            this.context = context;
            this.exercises = exercises;
            this.images = images;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View row = layoutInflater
                    .inflate(R.layout.element_list_exercises, parent, false);
            ImageView imagesIV = row.findViewById(R.id.imageViewExList);
            TextView myTitle = row.findViewById(R.id.textViewExList);

            imagesIV.setImageResource(images.get(position));
            myTitle.setText(exercises.get(position).getName());

            return row;
        }
    }
}