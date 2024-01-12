package com.example.mareu;

import com.example.mareu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.util.Calendar;

public class AddMeeting extends AppCompatActivity {
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

    private void saveMeeting(){
        if(startTime > endTime) {
            Toast.makeText(getApplicationContext(), "L'heure de fin doit être postérieure à l'heure de début", Toast.LENGTH_SHORT).show();
        }
        else {
            String subject = editTextSubject.getText().toString();
            String place = editTextPlace.getText().toString();
            String startHour = textStartHour.getText().toString();
            String endHour = textEndHour.getText().toString();

            if(subject.trim().isEmpty() || place.trim().isEmpty() || startHour.trim().isEmpty() || endHour.trim().isEmpty()){
                Toast.makeText(this, "Tout les champs doivent être remplis", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_SUBJECT, subject);
            data.putExtra(EXTRA_PLACE, place);
            data.putExtra(EXTRA_START_HOUR, startHour);
            data.putExtra(EXTRA_END_HOUR, endHour);

            setResult(RESULT_OK, data);
            finish();
        }
    }
}

