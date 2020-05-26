package com.example.fitnessapp.ui.journal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.fitnessapp.model.entities.Note;
import com.example.fitnessapp.ui.authentication.authorization.AuthorizationViewModel;

import java.util.ArrayList;
import java.util.List;

public class NoteChangeFragment extends Fragment {

    private SpinnerAdapter spinnerAdapter;

    private ListNotesNoteChangeSharedViewModel listNotesNoteChangeSharedViewModel;
    private AuthorizationViewModel authorizationViewModel;

    private int idWorkout;
    private int idNote;
    private List<Exercise> exerciseList;
    private Note note;

    public NoteChangeFragment(int idWorkout, int idNote) {
        this.idWorkout = idWorkout;
        this.idNote = idNote;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listNotesNoteChangeSharedViewModel =
                new ViewModelProvider(this).get(ListNotesNoteChangeSharedViewModel.class);
        authorizationViewModel =
                new ViewModelProvider(getActivity()).get(AuthorizationViewModel.class);

        note = authorizationViewModel.getUser().getJournal().get(idWorkout).getNotes().get(idNote);
        return inflater.inflate(R.layout.note_change, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = view.findViewById(R.id.spinner);
        EditText editTextNote = view.findViewById(R.id.editTextNote);
        Button buttonOk = view.findViewById(R.id.buttonOkNoteItem);

        List<Exercise> exercisesList = new ArrayList<>();

        spinnerAdapter = new SpinnerAdapter(getActivity(), exercisesList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        listNotesNoteChangeSharedViewModel.queryAllExercises().observe(getViewLifecycleOwner(), exercises -> {
            spinnerAdapter.setList(exercises);
            exercisesList.addAll(exercises);
            if(!note.isEmpty()) {
                spinner.setSelection(listNotesNoteChangeSharedViewModel
                        .getIndexExercise(exercises, note.getIdExerscise()));
                editTextNote.setText(note.getRecord());
            }
        });

        buttonOk.setOnClickListener(v -> {
            authorizationViewModel.getUser().getJournal()
                    .get(idWorkout).getNotes().get(idNote)
                    .setIdExerscise(exerciseList.get(spinner.getSelectedItemPosition()).getId());
            authorizationViewModel.getUser().getJournal().get(idWorkout).getNotes().get(idNote)
                    .setRecord(editTextNote.getText().toString());
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Exercise clickedItem = (Exercise) parent.getItemAtPosition(position);
                String clickedExerciseName = clickedItem.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    class SpinnerAdapter extends ArrayAdapter<Exercise> {

        private List<Exercise> exercisesList;

        SpinnerAdapter (Context context, List<Exercise> exercisesList) {
            super(context, R.layout.spinner_item, exercisesList);
            this.exercisesList = exercisesList;
        }

        public void setList(List<Exercise> exercisesList) {
            this.exercisesList.clear();
            this.exercisesList.addAll(exercisesList);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView,
                                    @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        private View initView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = layoutInflater
                        .inflate(R.layout.spinner_item, parent, false);
            }

            Exercise exercise = exercisesList.get(position);

            TextView textViewEx = convertView.findViewById(R.id.textViewSpinnerItem);
            ImageView imageViewEx = convertView.findViewById(R.id.imageViewSpinnerItem);

            if(exercise != null) {
                textViewEx.setText(exercise.getName());
            }

            return convertView;
        }

    }

}