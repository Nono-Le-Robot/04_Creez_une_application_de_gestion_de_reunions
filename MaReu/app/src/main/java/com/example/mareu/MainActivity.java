package com.example.mareu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.data.MeetingViewModel;
import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.model.Meeting;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_MEETING_REQUEST = 1;
    private MeetingViewModel meetingViewModel;
    private EditText editTextSearch;

    private MeetingAdapter adapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FloatingActionButton buttonAddMeeting = findViewById(R.id.btn_add_meeting);
        buttonAddMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMeeting.class);
                startActivityForResult(intent, ADD_MEETING_REQUEST);
            }
        });

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new MeetingAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MeetingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                meetingViewModel.delete(adapter.getMeetingAt(position));
                Toast.makeText(MainActivity.this, "Meeting deleted", Toast.LENGTH_SHORT).show();
            }
        });

        meetingViewModel = new ViewModelProvider(this).get(MeetingViewModel.class);
        meetingViewModel.getAllMeetings().observe(this, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                Collections.sort(meetings, new Comparator<Meeting>() {
                    @Override
                    public int compare(Meeting meeting1, Meeting meeting2) {
                        return Long.compare(meeting1.getStartMeetingHour(), meeting2.getStartMeetingHour());
                    }
                });

                for (Meeting meeting : meetings) {
                    Log.d("Meeting", meeting.toString());
                }

                adapter.setMeetings(meetings);
            }
        });
    }

    public void goBack(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Souhaitez-vous vraiment quitter l'application ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public void searchFor(View view){
        showSearchForDialog();
    }

    public void resetFilter(View view){
        adapter.getFilter().filter("");
    }

    public void generateMeetings(View view) {
        meetingViewModel.deleteAllMeetings();

        List<Meeting> meetings = new ArrayList<>();

        meetings.add(new Meeting(1642591200000L, 1642598400000L, "8", "Dev talk", new ArrayList<>(Arrays.asList("test@gmail.com", "test2@gmail.com", "test3@gmail.com")).toString()));
        meetings.add(new Meeting(1642598400000L, 1642605600000L, "3", "Design meeting", new ArrayList<>(Arrays.asList("alice@gmail.com", "bob@gmail.com")).toString()));
        meetings.add(new Meeting(1642605600000L, 1642612800000L, "3", "Project review", new ArrayList<>(Arrays.asList("charlie@gmail.com", "david@gmail.com", "emma@gmail.com")).toString()));
        meetings.add(new Meeting(1642612800000L, 1642620000000L, "1", "Team brainstorming", new ArrayList<>(Arrays.asList("frank@gmail.com", "grace@gmail.com")).toString()));

        for (Meeting meeting : meetings) {
            meetingViewModel.insert(meeting);
        }
    }



    private void showSearchForDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_search, null);

        Spinner spinnerSearchType = view.findViewById(R.id.spinner_search_type);
        editTextSearch = view.findViewById(R.id.edit_text_searchFor);
        spinnerSearchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1) {
                    editTextSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else if (position == 3 || position == 4) {
                    editTextSearch.setInputType(InputType.TYPE_NULL);
                    showTimePicker();
                } else {
                    editTextSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        builder.setView(view);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Spinner selectedPosition = spinnerSearchType;

                String searchText = editTextSearch.getText().toString();

                if (TextUtils.isEmpty(searchText)) {
                    adapter.getFilter().filter("");
                    return;
                }

                if (selectedPosition.getSelectedItem().equals("Meeting Subject")) {
                    adapter.searchForType(0);
                    adapter.getFilter().filter(searchText);
                }

                if (selectedPosition.getSelectedItem().equals("Room Number")) {
                    adapter.searchForType(1);
                    adapter.getFilter().filter(searchText);
                }

                if (selectedPosition.getSelectedItem().equals("Participant")) {
                    adapter.searchForType(2);
                    adapter.getFilter().filter(searchText);
                }

                if (selectedPosition.getSelectedItem().equals("Start at")) {
                    adapter.searchForType(3);
                    adapter.getFilter().filter(searchText);
                }

                if (selectedPosition.getSelectedItem().equals("End at")) {
                    adapter.searchForType(4);
                    adapter.getFilter().filter(searchText);
                }

            }
        });

        builder.setNegativeButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.getFilter().filter("");
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showTimePicker() {
        editTextSearch.requestFocus();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                editTextSearch.setText(selectedTime);
            }
        }, 0, 0, true);
        timePickerDialog.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_MEETING_REQUEST && resultCode == RESULT_OK){

            String subject = data.getStringExtra(AddMeeting.EXTRA_SUBJECT);
            String place = data.getStringExtra(AddMeeting.EXTRA_PLACE);
            String startHour = data.getStringExtra(AddMeeting.EXTRA_START_HOUR);
            String endHour = data.getStringExtra(AddMeeting.EXTRA_END_HOUR);
            String participants = data.getStringExtra(AddMeeting.EXTRA_PARTICIPANTS);
            Log.d("participants", participants);


            Meeting meeting = new Meeting(Long.parseLong(startHour),Long.parseLong(endHour),place,subject,participants);
            meetingViewModel.insert(meeting);
        }

    }


}

//TODO : le filtrage par plage d'heure (afficher toutes les reunions dont le début et dans cette plage d'heure) (OK)
//TODO : faire le systeme de tri (OK)
//TODO : mettre en place l'indicateur coloré en face de chaque réunion (OK)
//TODO : finir la validation des données (OK)
//TODO : voir pour l'accéssibilité
//TODO : JAVADOC
//TODO : définir les fonctionalités et écrire les tests
//TODO : faire le PowerPoint de présentation
