package com.example.fitnessapp.ui.journal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.Exercise;
import com.example.fitnessapp.model.entities.Note;
import com.example.fitnessapp.ui.authentication.authorization.AuthorizationViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListNotesFragment extends Fragment {

    private ListView listView;
    private ListNotesNoteChangeSharedViewModel listNotesNoteChangeSharedViewModel;
    private AuthorizationViewModel authorizationViewModel;

    private List<Exercise> exercises;
    private List<Note> notes;
    private Adapter adapter;
    private int indexWorkout;

    ListNotesFragment(int indexWorkout) {
        exercises = new ArrayList<>();
        notes = new ArrayList<>();
        this.indexWorkout = indexWorkout;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listNotesNoteChangeSharedViewModel = new ViewModelProvider(getActivity())
                .get(ListNotesNoteChangeSharedViewModel.class);
        authorizationViewModel = new ViewModelProvider(getActivity())
                .get(AuthorizationViewModel.class);

        notes = authorizationViewModel.getUser().getJournal().get(indexWorkout).getNotes();
        return inflater.inflate(R.layout.list_notes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listViewNotes);
        Button buttonDeleteWorkout = view.findViewById(R.id.buttonDelete);
        Button buttonAddNote = view.findViewById(R.id.buttonAddNote);

        buttonDeleteWorkout.setOnClickListener(v -> {
            authorizationViewModel.setUserDeleteWorkout(authorizationViewModel
                    .getUser().getJournal().get(indexWorkout));

            JournalFragment journalFragment = new JournalFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, journalFragment);
            transaction.commit();
        });

        adapter = new Adapter(getActivity(), notes);

        buttonAddNote.setOnClickListener(v -> {
            notes.add(new Note());
            adapter.notifyDataSetChanged();
        });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
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

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = layoutInflater
                        .inflate(R.layout.notes_list_item, parent, false);
            }
            TextView textViewEx = convertView.findViewById(R.id.textViewNotesListItemEx);
            TextView textViewRec = convertView.findViewById(R.id.textViewNotesListItemRec);
            Button buttonDelete = convertView.findViewById(R.id.buttonDeleteNote);

            buttonDelete.setOnClickListener(v -> {
                authorizationViewModel.setUserDeleteNote(notesList.get(position));
                notesList.remove(position);
                notifyDataSetChanged();
            });

            Note note = notesList.get(position);

            textViewEx.setText(listNotesNoteChangeSharedViewModel
                    .getExerciseById(note.getIdExerscise()).getName());
            textViewRec.setText(note.getRecord());

            return convertView;
        }
    }
}