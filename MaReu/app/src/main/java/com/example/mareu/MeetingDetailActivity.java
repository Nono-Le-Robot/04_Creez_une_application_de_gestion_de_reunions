package com.example.mareu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class MeetingDetailActivity extends AppCompatActivity {
    private TextView textViewDetailMeetingHour;
    private TextView textViewDetailMeetingSubject;
    private TextView textViewDetailMeetingPlace;
    private RecyclerView recyclerViewParticipants;
    private ParticipantAdapter participantAdapter;
    private FloatingActionButton buttonGoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail);

        recyclerViewParticipants = findViewById(R.id.recyclerViewParticipants);
        recyclerViewParticipants.setLayoutManager(new LinearLayoutManager(this));

        Intent intentParticipants = getIntent();
        if (intentParticipants != null) {
            String meetingParticipants = intentParticipants.getStringExtra("meetingParticipants");
            meetingParticipants = meetingParticipants.replaceAll("\\[|\\]", "");
            List<String> participantsList = Arrays.asList(meetingParticipants.split(", "));

            participantAdapter = new ParticipantAdapter(this, participantsList);
            recyclerViewParticipants.setAdapter(participantAdapter);
        }

        Toolbar toolbar = findViewById(R.id.back_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

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
                participant = participant.trim();
            }

            textViewDetailMeetingHour = findViewById(R.id.detail_meeting_hour);
            textViewDetailMeetingSubject = findViewById(R.id.detail_meeting_subject);
            textViewDetailMeetingPlace = findViewById(R.id.detail_meeting_place);

            textViewDetailMeetingHour.setText(meetingStartHour + " - " + meetingEndHour);
            textViewDetailMeetingSubject.setText(meetingSubject);
            textViewDetailMeetingPlace.setText("Room " + meetingPlace);
        }
    }

    public void goBack(View view) {
        onBackPressed();
    }
}