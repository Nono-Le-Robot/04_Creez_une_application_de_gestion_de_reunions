package com.example.mareu;

import com.example.mareu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

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
    private EditText editTextStartHour;
    private EditText editTextEndHour;

    private FloatingActionButton btnSaveMeeting;

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
        editTextStartHour = findViewById(R.id.edit_text_start_hour);
        editTextEndHour = findViewById(R.id.edit_text_end_hour);

        btnSaveMeeting = findViewById(R.id.btn_save_meeting);

        btnSaveMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             saveMeeting();

            }
        });
    }

    private void saveMeeting(){
        String subject = editTextSubject.getText().toString();
        String place = editTextPlace.getText().toString();
        String startHour = editTextStartHour.getText().toString();
        String endHour = editTextEndHour.getText().toString();

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
        Log.d("test", "okokokokokokokok");


    }

}

