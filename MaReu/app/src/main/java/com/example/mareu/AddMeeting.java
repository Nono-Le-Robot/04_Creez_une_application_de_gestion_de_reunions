package com.example.mareu;

import com.example.mareu.R;
import com.example.mareu.data.MeetingViewModel;
import com.example.mareu.model.Meeting;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMeeting extends AppCompatActivity {

    private MeetingViewModel meetingViewModel;

    public static final String EXTRA_SUBJECT =
            "com.example.mareu.EXTRA_SUBJECT";
    public static final String EXTRA_PLACE =
            "com.example.mareu.EXTRA_PLACE";
    public static final String EXTRA_START_HOUR =
            "com.example.mareu.EXTRA_START_HOUR";
    public static final String EXTRA_END_HOUR =
            "com.example.mareu.EXTRA_END_HOUR";

    private EditText editTextPlace;
    private EditText editTextSubject;
    private TextView textStartHour;
    private TextView textEndHour;
    private Button setStartHourBtn;
    private Button setEndHourBtn;
    private FloatingActionButton btnSaveMeeting;

    private long startTime;

    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        editTextPlace = findViewById(R.id.edit_text_place);
        editTextSubject = findViewById(R.id.edit_text_subject);
        textStartHour = findViewById(R.id.edit_text_start_hour);
        textEndHour = findViewById(R.id.edit_text_end_hour);

        setStartHourBtn = findViewById(R.id.set_start_hour_btn);
        setEndHourBtn = findViewById(R.id.set_end_hour_btn);
        btnSaveMeeting = findViewById(R.id.btn_save_meeting);

        textStartHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click-debug", "clickeeeedddd");
                openTimePickerDialog("start");
            }
        });

        textEndHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog("end");
            }
        });

        setStartHourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog("start");
            }
        });
        setEndHourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog("end");
            }
        });

        btnSaveMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             saveMeeting();

            }
        });
    }

    private void openTimePickerDialog(String tag){

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        //TODO : verifier qu'il y a une sécurité si on ajoute une nouvelle réunion

        TimePickerDialog myTimePicker;

        myTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (tag.equals("start")){
                    startTime = convertToMillis(hourOfDay, minute);
                    textStartHour.setText(formatTime(hourOfDay, minute));
                }
                if (tag.equals("end")){
                    endTime = convertToMillis(hourOfDay, minute);
                    textEndHour.setText(formatTime(hourOfDay, minute));
                }
            }

            private long convertToMillis(int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                return calendar.getTimeInMillis();
            }

            private String formatTime(int hourOfDay, int minute) {
                String minuteString = (minute < 10) ? "0" + minute : String.valueOf(minute);
                return hourOfDay + " : " + minuteString;
            }

        },hour,min, true);
        myTimePicker.show();


    }

    private boolean isRoomAvailable(String room, long startTime, long endTime){
        // ici c'est les valeurs de la salle a sauvegarder :
        Log.d("debug-take", "a sauvegarder : " +" salle : " + room + " start : " + startTime + " end : " + endTime);

        //verifier si une salle est déja occupée
        meetingViewModel = new ViewModelProvider(this).get(MeetingViewModel.class);
        meetingViewModel.getAllMeetings().observe(this, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                for (Meeting meeting : meetings) {
                    // ici j'obtien les infos des salles deja en BDD
                    Log.d("debug-take", String.valueOf(meeting.getMeetingPlace()));

                }

            }
        });


        return false;
    }
    private void saveMeeting(){
        String subject = editTextSubject.getText().toString();
        String place = editTextPlace.getText().toString();
        long startHour = startTime;
        long endHour = endTime;


        //verifier que l'heure de fin est bien apres l'heure de début
        if(startTime > endTime) {
            Toast.makeText(getApplicationContext(), "L'heure de fin doit être postérieure à l'heure de début", Toast.LENGTH_SHORT).show();
        } else {
            if(subject.trim().isEmpty() || place.trim().isEmpty()){
                Toast.makeText(this, "Tout les champs doivent être remplis", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_SUBJECT, subject);
            data.putExtra(EXTRA_PLACE, place);
            data.putExtra(EXTRA_START_HOUR, String.valueOf(startHour));
            data.putExtra(EXTRA_END_HOUR, String.valueOf(endHour));


            Log.d("debug-db-save", subject + "   " + place + "   " + startHour + "   " + endHour);

            setResult(RESULT_OK, data);
            finish();
        }
    }
}

