package com.example.fitnessapp.ui.journal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.Exercise;

import java.util.List;

public class NoteChangeFragment extends Fragment {

    private ListNotesNoteChangeSharedViewModel listNotesNoteChangeSharedViewModel;
    private List<Exercise> exercisesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listNotesNoteChangeSharedViewModel =
                new ViewModelProvider(this).get(ListNotesNoteChangeSharedViewModel.class);
        View root = inflater.inflate(R.layout.note_change, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = view.findViewById(R.id.spinner);
        EditText editTextNote = view.findViewById(R.id.editTextNote);
        Button buttonOk = view.findViewById(R.id.buttonOkNoteItem);
        Button buttonDelete = view.findViewById(R.id.buttonDeleteNoteItem);

        listNotesNoteChangeSharedViewModel.queryAllExercises().observe(getViewLifecycleOwner(),
                exercises -> {
            exercisesList = exercises;
            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(exercisesList);
            spinner.setAdapter(spinnerAdapter);
        });
    }

    class SpinnerAdapter extends BaseAdapter {

        private List<Exercise> exercisesList;

        SpinnerAdapter (List<Exercise> exercisesList) {
            this.exercisesList = exercisesList;
        }

        @Override
        public int getCount() {
            return this.exercisesList.size();
        }

        @Override
        public Object getItem(int position) {
            return exercisesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null) {
                convertView = layoutInflater
                        .inflate(R.layout.spinner_item, parent, false);
            }

            Exercise exercise = exercisesList.get(position);

            TextView textViewEx = convertView.findViewById(R.id.textViewSpinnerItem);
            ImageView imageViewEx = convertView.findViewById(R.id.imageViewSpinnerItem);

            textViewEx.setText(exercise.getName());

            return convertView;
        }
    }
}