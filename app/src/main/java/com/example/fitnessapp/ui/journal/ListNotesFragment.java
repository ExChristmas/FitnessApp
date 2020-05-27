package com.example.fitnessapp.ui.journal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListNotesFragment extends Fragment {

    private ListView listView;
    private ListNotesNoteChangeSharedViewModel listNotesNoteChangeSharedViewModel;
    private AuthorizationViewModel authorizationViewModel;
    private ActionBar actionBar;

    private List<Exercise> exercisesList;
    private List<Note> notes;
    private Adapter adapter;
    private Button buttonAddNote;
    private int indexWorkout;

    ListNotesFragment(int indexWorkout) {
        exercisesList = new ArrayList<>();
        notes = new ArrayList<>();
        this.indexWorkout = indexWorkout;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listNotesNoteChangeSharedViewModel = new ViewModelProvider(getActivity())
                .get(ListNotesNoteChangeSharedViewModel.class);
        authorizationViewModel = new ViewModelProvider(getActivity())
                .get(AuthorizationViewModel.class);

        actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.list_notes, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // put lost notes on DB
//        listNotesNoteChangeSharedViewModel.putNotesOnDB(notes);
        authorizationViewModel.getUser().getJournal().get(indexWorkout).setNotes(notes);

        JournalFragment journalFragment = new JournalFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, journalFragment);
        transaction.commit();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(false);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listViewNotes);
        buttonAddNote = view.findViewById(R.id.buttonAddNote);
        adapter = new Adapter(getActivity(), notes);

        listNotesNoteChangeSharedViewModel.queryAllExercises()
                .observe(getViewLifecycleOwner(), exercises -> {
                    exercisesList.addAll(exercises);
                    notes = authorizationViewModel.getUser()
                            .getJournal().get(indexWorkout).getNotes();
                    adapter.setList(notes);
                });

        buttonAddNote.setOnClickListener(v -> {
            Note note = new Note();
            note.setId(UUID.randomUUID().toString());
            note.setIdWorkout(authorizationViewModel.getUser().getJournal()
                    .get(indexWorkout).getId());
            notes.add(note);
            adapter.setList(notes);
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            // put lost notes on DB
//            listNotesNoteChangeSharedViewModel.putNotesOnDB(notes);
            authorizationViewModel.getUser().getJournal().get(indexWorkout).setNotes(notes);
            NoteChangeFragment noteChangeFragment = new NoteChangeFragment(indexWorkout, position);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, noteChangeFragment);
            transaction.commit();
        });

    }

    class Adapter extends ArrayAdapter<Note> {

        private List<Note> notesList;

        Adapter(Context context, List<Note> notesList) {
            super(context, R.layout.notes_list_item, notesList);
            this.notesList = notesList;
        }

        public void setList(List<Note> notesList) {
            this.notesList.clear();
            this.notesList.addAll(notesList);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = layoutInflater
                        .inflate(R.layout.notes_list_item, parent, false);
            }

            TextView textViewEx = convertView.findViewById(R.id.textViewNotesListItemEx);
            TextView textViewRec = convertView.findViewById(R.id.textViewNotesListItemRec);
            Button buttonDelete = convertView.findViewById(R.id.buttonDeleteNote);

            buttonDelete.setOnClickListener(v -> {
//                authorizationViewModel.setUserDeleteNote(notesList.get(position));
                notesList.remove(position);
                notes.remove(position);
                notifyDataSetChanged();
            });

            Note note = notesList.get(position);

            if (note.getIdExerscise() != null && !exercisesList.isEmpty()) {
                textViewEx.setText(exercisesList.get(listNotesNoteChangeSharedViewModel
                        .getIndexExercise(exercisesList, note.getIdExerscise())).getName());
                textViewRec.setText(note.getRecord());
            } else {
                textViewEx.setText("Упраждение");
                textViewRec.setText("Запись");
            }
            return convertView;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }
    }
}