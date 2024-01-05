package com.example.mareu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import java.util.Arrays;
import java.util.List;

public class MeetingDetailActivity extends AppCompatActivity {
    private TextView textViewDetailMeetingHour;
    private TextView textViewDetailMeetingSubject;
    private TextView textViewDetailMeetingPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Centrer le titre dans la Toolbar
        Intent intent = getIntent();
        if (intent != null) {
            int meetingId = intent.getIntExtra("meetingId", 0);
            String meetingStartHour = intent.getStringExtra("meetingStartHour");
            String meetingEndHour = intent.getStringExtra("meetingEndHour");
            String meetingPlace = intent.getStringExtra("meetingPlace");
            String meetingSubject = intent.getStringExtra("meetingSubject");
            String meetingParticipants = intent.getStringExtra("meetingParticipants");

            meetingParticipants = meetingParticipants.replaceAll("\\[|\\]", "");
            List<String> participantsList = Arrays.asList(meetingParticipants.split(", "));
            for (String participant : participantsList) {
                Log.d("participants", participant);
                //
            }

            textViewDetailMeetingHour = findViewById(R.id.detail_meeting_hour);
            textViewDetailMeetingSubject = findViewById(R.id.detail_meeting_subject);
            textViewDetailMeetingPlace = findViewById(R.id.detail_meeting_place);

            textViewDetailMeetingHour.setText(meetingStartHour + " - " + meetingEndHour);
            textViewDetailMeetingSubject.setText(meetingSubject);
            textViewDetailMeetingPlace.setText(meetingPlace);

        }
    }
}