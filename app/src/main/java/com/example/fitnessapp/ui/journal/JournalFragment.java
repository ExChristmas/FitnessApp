package com.example.fitnessapp.ui.journal;

import android.app.DatePickerDialog;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.Note;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.entities.Workout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class JournalFragment extends Fragment {

    private JournalViewModel journalViewModel;
    private ListView listView;
    private MutableLiveData<User> userLiveData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        journalViewModel =
                ViewModelProviders.of(this).get(JournalViewModel.class);
        View root = inflater.inflate(R.layout.journal_fragment, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listViewWorkouts);
        User user = new User();
        user.setEmail("averamenkoav98@mail.ru");
        user.setName("Alex");
        user.setSurname("Avaramenko");
        List<Workout> workoutsList = new ArrayList<>();
        Workout workout = new Workout();
        workout.setId("1");
        workout.setIdUser("avramenkoav98@mail.ru");
        workout.setDate(new GregorianCalendar(2020, 3, 1));
        List<Note> noteList = new ArrayList<>();
        workout.setNotes(noteList);
        workoutsList.add(workout);
        user.setJournalWorkout(workoutsList);

        DatePickerDialog datePickerDialog ;

        userLiveData = new MutableLiveData<>();

        Adapter adapter = new Adapter(getActivity(), workoutsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            userLiveData.setValue(user);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        userLiveData.observeForever(exercise -> {
            ListNotesFragment listNotesFragment = new ListNotesFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, listNotesFragment);
            transaction.commit();
        });
    }


    class Adapter extends ArrayAdapter<Workout> {

        private List<Workout> workoutList;

        Adapter(Context context, List<Workout> workoutList) {
            super(context, R.layout.workouts_list_elem, workoutList);
            this.workoutList = workoutList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = layoutInflater
                        .inflate(R.layout.workouts_list_elem, parent, false);
            }
                TextView date = convertView.findViewById(R.id.textViewDateWorkout);
            Workout workout = workoutList.get(position);
            String dateString = workout.getDate().get(Calendar.YEAR) + "-" +
                    (workout.getDate().get(Calendar.MONTH) + 1) + "-" +
                    workout.getDate().get(Calendar.DAY_OF_MONTH);
            date.setText(dateString);

            return convertView;
        }
    }
}