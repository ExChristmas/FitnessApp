package com.example.fitnessapp.model.dao.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.model.dao.ConectionDB;
import com.example.fitnessapp.model.entities.Note;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.entities.Workout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserActionsGlobalDB implements ConectionDB {

    private FirebaseFirestore db;

    public UserActionsGlobalDB() {
        db = FirebaseFirestore.getInstance();
    }

    public void add(User user) {
        Map<String, Object> objectUser = new HashMap<>();
        objectUser.put("name", user.getName());
        objectUser.put("surname", user.getSurname());
        objectUser.put("email", user.getEmail());
        List<Map<String, Object>> listWorkouts = new ArrayList<>();
        for (Workout workout : user.getJournal()) {
            Map<String, Object> workoutObject = new HashMap<>();
            workoutObject.put("id", workout.getId());
            String date = workout.getDate().get(Calendar.YEAR) + "-" +
                    (workout.getDate().get(Calendar.MONTH) + 1) + "-" +
                    workout.getDate().get(Calendar.DAY_OF_MONTH);
            workoutObject.put("date", date);
            List<Map<String, Object>> listNotes = new ArrayList<>();
            for(Note note : workout.getNotes()) {
                Map<String, Object> noteObject = new HashMap<>();
                noteObject.put("id", note.getId());
                noteObject.put("record", note.getRecord());
                noteObject.put("id_exercise", note.getIdExerscise());
                noteObject.put("id_workout", workout.getId());
                listNotes.add(noteObject);
            }
            workoutObject.put("notes", listNotes);
            listWorkouts.add(workoutObject);
        }
        objectUser.put("workouts", listWorkouts);

        db.collection("user").document(user.getEmail())
                .set(objectUser)
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                });
    }

    public void remove(User user) {
        db.collection("user").document(user.getEmail()).delete()
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                });
    }

    public MutableLiveData<User> getByEmail(String email) {
        return null;
    }

    public LiveData<User> getById(String id) {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
            DocumentReference dr = this.db.collection("user").document(id);
            dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String userId = document.getId();
                                Map<String, Object> objectMap = document.getData();
                                User user = new User();
                                user.setEmail(Objects.requireNonNull(objectMap.get("email")).toString());
                                user.setName(Objects.requireNonNull(objectMap.get("name")).toString());
                                user.setSurname(Objects.requireNonNull(objectMap.get("surname")).toString());
                                List<Map<String, Object>> journalListMap = (List<Map<String, Object>>) Objects
                                        .requireNonNull(objectMap.get("workouts"));
                                List<Workout> journalList = new ArrayList<>();
                                for (int i = 0; i < journalListMap.size(); i++) {
                                    Workout workout = new Workout();
                                    Map<String, Object> workoutMap = journalListMap.get(i);

                                    workout.setId(Objects.requireNonNull(workoutMap.get("id")).toString()); //запись id
                                    String[] ymd = Objects.requireNonNull(workoutMap.get("date")).toString().split("-");
                                    GregorianCalendar date = new GregorianCalendar(Integer.parseInt(ymd[0]),
                                            Integer.parseInt(ymd[1]), Integer.parseInt(ymd[2]));
                                    workout.setDate(date); // запись даты
                                    workout.setIdUser(userId); // запись id юзера
                                    List<Map<String, Object>> listNotesMap = (List<Map<String, Object>>) Objects
                                            .requireNonNull(workoutMap.get("notes"));
                                    List<Note> listNotes = new ArrayList<>();
                                    for (int j = 0; j < listNotesMap.size(); j++) {
                                        Note note = new Note();
                                        Map<String, Object> noteMap = listNotesMap.get(j);

                                        note.setId(Objects.requireNonNull(noteMap.get("id")).toString());
                                        note.setRecord(Objects.requireNonNull(noteMap.get("record")).toString());
                                        note.setIdWorkout(Objects.requireNonNull(noteMap.get("id_workout")).toString());
                                        note.setIdExerscise(Objects.requireNonNull(noteMap.get("id_exercise")).toString());
                                        listNotes.add(note);
                                    }
                                    workout.setNotes(listNotes);
                                    journalList.add(workout);
                                }
                                user.setJournalWorkout(journalList);
                                userLiveData.setValue(user);
                            }
                        }
                    }
                }
            });

        return userLiveData;
    }

    @Override
    public void disconnect() {
    }

}