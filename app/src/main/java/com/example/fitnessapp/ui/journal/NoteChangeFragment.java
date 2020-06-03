package com.example.fitnessapp.ui.journal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.Exercise;
import com.example.fitnessapp.model.entities.Note;
import com.example.fitnessapp.ui.authentication.authorization.AuthorizationViewModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NoteChangeFragment extends Fragment {

    private SpinnerAdapter spinnerAdapter;

    private ListNotesNoteChangeSharedViewModel listNotesNoteChangeSharedViewModel;
    private AuthorizationViewModel authorizationViewModel;
    private ActionBar actionBar;

    private int idWorkout;
    private int idNote;
    private List<Exercise> exerciseList;
    private Note note;

    public NoteChangeFragment(int idWorkout, int idNote) {
        this.idWorkout = idWorkout;
        this.idNote = idNote;
        exerciseList = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listNotesNoteChangeSharedViewModel =
                new ViewModelProvider(this).get(ListNotesNoteChangeSharedViewModel.class);

        authorizationViewModel =
                new ViewModelProvider(getActivity()).get(AuthorizationViewModel.class);

        actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        note = authorizationViewModel.getUser().getJournal()
                .get(idWorkout).getNotes().get(idNote);
        return inflater.inflate(R.layout.note_change, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ListNotesFragment listNotesFragment = new ListNotesFragment(idWorkout);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, listNotesFragment);
        transaction.commit();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(false);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = view.findViewById(R.id.spinner);
        EditText editTextNote = view.findViewById(R.id.editTextNote);
        Button buttonOk = view.findViewById(R.id.buttonOkNoteItem);

        spinnerAdapter = new SpinnerAdapter(getActivity(), exerciseList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

//        // если есть интернет, грузим упражнения из глобальной базы
//        if(InternetConnection.isConnect(getContext())) {
//            listNotesNoteChangeSharedViewModel.queryAllExercises()
//                    .observe(getViewLifecycleOwner(), exercises -> {
//                        exerciseList.addAll(exercises);
//                        if (!note.isEmpty()) {
//                            spinner.setSelection(listNotesNoteChangeSharedViewModel
//                                    .getIndexExercise(exerciseList, note.getIdExerscise()));
//                            editTextNote.setText(note.getRecord());
//                        }
//                        spinnerAdapter.setList(exercises);
//                    });
//        } else { // иначе, загружаем из локальной базы
            List<Exercise> exercises = listNotesNoteChangeSharedViewModel
                    .queryAllExercisesLocal();
            exerciseList.addAll(exercises);
            if (!note.isEmpty()) {
                spinner.setSelection(listNotesNoteChangeSharedViewModel
                        .getIndexExercise(exerciseList, note.getIdExerscise()));
                editTextNote.setText(note.getRecord());
            }
            spinnerAdapter.setList(exercises);
//        }

        buttonOk.setOnClickListener(v -> {
            // обновляем упражнение
            authorizationViewModel.getUser().getJournal()
                    .get(idWorkout).getNotes().get(idNote)
                    .setIdExerscise(exerciseList.get(spinner.getSelectedItemPosition()).getId());
            // обновляем запись об упражнении
            authorizationViewModel.getUser().getJournal().get(idWorkout).getNotes().get(idNote)
                    .setRecord(editTextNote.getText().toString());

            ListNotesFragment listNotesFragment = new ListNotesFragment(idWorkout);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, listNotesFragment);
            transaction.commit();
        });
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

    class SpinnerAdapter extends ArrayAdapter<Exercise> {

        private List<Exercise> exercisesList;

        SpinnerAdapter(Context context, List<Exercise> exercisesList) {
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

            if (exercise != null) {
                textViewEx.setText(exercise.getName());
                imageViewEx.setImageResource(getId(exercise.getId(), R.drawable.class));
            }

            return convertView;
        }
    }
}