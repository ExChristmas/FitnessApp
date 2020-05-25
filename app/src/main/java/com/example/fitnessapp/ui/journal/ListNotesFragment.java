package com.example.fitnessapp.ui.journal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.Exercise;
import com.example.fitnessapp.model.entities.Note;
import com.example.fitnessapp.model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ListNotesFragment extends Fragment {

    private ListView listView;
    private ListNotesNoteChangeSharedViewModel listNotesNoteChangeSharedViewModel;

    private List<Exercise> exercisesList;
    private List<Note> listNote;
    private MutableLiveData<User> workoutLiveData;

    ListNotesFragment() {
        exercisesList = new ArrayList<>();
        listNote = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listNotesNoteChangeSharedViewModel = new ViewModelProvider(getActivity())
                .get(ListNotesNoteChangeSharedViewModel.class);

        View root = inflater.inflate(R.layout.list_notes, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listViewNotes);
            Note note = null;
            listNote.add(note);
            Adapter adapter = new Adapter(getActivity(), listNote);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view1, position, id) -> {
//                listNote.get(position);
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

            Note note = notesList.get(position);

            return convertView;
        }
    }

}