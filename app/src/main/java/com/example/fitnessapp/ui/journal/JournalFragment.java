package com.example.fitnessapp.ui.journal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.entities.Workout;
import com.example.fitnessapp.ui.authentication.authorization.AuthorizationViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class JournalFragment extends Fragment {

    private JournalViewModel journalViewModel;
    private AuthorizationViewModel authorizationViewModel;
    private ListView listView;
    private List<Workout> workouts;
    private boolean fragmentWhithoutUser;

    private Adapter adapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        journalViewModel =
                new ViewModelProvider(getActivity()).get(JournalViewModel.class);
        authorizationViewModel =
                new ViewModelProvider(getActivity()).get(AuthorizationViewModel.class);

        fragmentWhithoutUser = true;

        User user = authorizationViewModel.getUser();

        if(user != null) {
            return inflater.inflate(R.layout.journal_fragment, container, false);
        } else {
            fragmentWhithoutUser = false;
            return inflater.inflate(R.layout.fragment_without_user, container, false);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(fragmentWhithoutUser) {
            super.onViewCreated(view, savedInstanceState);
            listView = view.findViewById(R.id.listViewWorkouts);
            Button buttonAddWorkout = view.findViewById(R.id.buttonAddWorkout);

            workouts = authorizationViewModel.getUser().getJournal();

            adapter = new Adapter(getActivity(), workouts);

            buttonAddWorkout.setOnClickListener(v -> {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            });

            mDateSetListener = (datePicker, year, month, day) -> {
                month++;

                // Формируем объект
                Workout workout = new Workout();
                GregorianCalendar gregorianCalendar = new GregorianCalendar(year,
                        month, day);
                //Задаём UUID
                workout.setId(UUID.randomUUID().toString());
                workout.setDate(gregorianCalendar);
                workout.setIdUser(authorizationViewModel.getUser().getEmail());
                workout.setNotes(new ArrayList<>());
                // put in DB!!!
//                journalViewModel.putInDB(workout);

                // Кидаем в список
                workouts.add(workout);
                adapter.notifyDataSetChanged();
            };

            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view1, position, id) -> {
                // Обновляем журнал юзера
                authorizationViewModel.setUserWorkouts(workouts);
                // Переходим на фрагмент списка записей
                ListNotesFragment listNotesFragment = new ListNotesFragment(position);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, listNotesFragment);
                transaction.commit();
            });
        }
    }

    class Adapter extends ArrayAdapter<Workout> {

        private List<Workout> workoutList;

        Adapter(Context context, List<Workout> workoutList) {
            super(context, R.layout.workouts_list_elem, workoutList);
            this.workoutList = workoutList;
        }

        public void setList(List<Workout> workoutList) {
            this.workoutList.clear();
            this.workoutList.addAll(workoutList);
            notifyDataSetChanged();
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
            Button buttonDelete = convertView.findViewById(R.id.buttonDeleteWorkout);
            buttonDelete.setOnClickListener(v -> {
                //delete workout on DB
//                journalViewModel.deleteInDB(workoutList.get(position));
                workoutList.remove(position);
                notifyDataSetChanged();
            });
            Workout workout = workoutList.get(position);
            String dateString = workout.getDate().get(Calendar.YEAR) + "-" +
                    (workout.getDate().get(Calendar.MONTH) + 1) + "-" +
                    workout.getDate().get(Calendar.DAY_OF_MONTH);
            date.setText(dateString);

            return convertView;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }
    }
}