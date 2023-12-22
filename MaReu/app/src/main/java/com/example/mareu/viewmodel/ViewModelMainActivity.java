package com.example.mareu.viewmodel;

import android.os.Bundle;
import android.util.Log;

import com.example.mareu.data.api.InitApi;
import com.example.mareu.data.api.MeetingApiService;
import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewModelMainActivity {
    public static void processData() {
        MeetingApiService mApiService = InitApi.getMeetingApiService();
        List<Meeting> meetings = mApiService.getMeetings();

        for (Meeting meeting : meetings) {
            Log.d("viewMeetings", "Meeting ID: " + meeting.getId());
            Log.d("viewMeetings", "Start Hour: " + meeting.getStartMeetingHour());
            Log.d("viewMeetings", "End Hour: " + meeting.getEndMeetingHour());
            Log.d("viewMeetings", "Place: " + meeting.getMeetingPlace());
            Log.d("viewMeetings", "Subject: " + meeting.getMeetingSubject());
            Log.d("viewMeetings", "Participants: " + meeting.getParticipants().toString());
            Log.d("viewMeetings", "-------------------");
        }

        Log.d("viewMeetings", "-----------------------------");
        Log.d("viewMeetings", "-----------------------------");
        Log.d("viewMeetings", "--------CREATE NEW-----------");
        Log.d("viewMeetings", "-----------------------------");
        Log.d("viewMeetings", "-----------------------------");

        Meeting newMeeting = new Meeting(
                meetings.size(),
                "14:00",
                "18:00",
                "Room 101",
                "New test Meeting",
                new ArrayList<>(Arrays.asList("participant1@test.com", "participant2@test.com", "participant3@test.com"))
        );

        mApiService.createMeeting(newMeeting);

        for (Meeting meeting : meetings) {
            Log.d("viewMeetings", "Meeting ID: " + meeting.getId());
            Log.d("viewMeetings", "Start Hour: " + meeting.getStartMeetingHour());
            Log.d("viewMeetings", "End Hour: " + meeting.getEndMeetingHour());
            Log.d("viewMeetings", "Place: " + meeting.getMeetingPlace());
            Log.d("viewMeetings", "Subject: " + meeting.getMeetingSubject());
            Log.d("viewMeetings", "Participants: " + meeting.getParticipants().toString());
            Log.d("viewMeetings", "-------------------");
        }

        Log.d("viewMeetings", "-----------------------------");
        Log.d("viewMeetings", "-----------------------------");
        Log.d("viewMeetings", "--------DELETE ID 2----------");
        Log.d("viewMeetings", "-----------------------------");
        Log.d("viewMeetings", "-----------------------------");

        mApiService.deleteMeeting(mApiService.getMeeting(2));

        for (Meeting meeting : meetings) {
            Log.d("viewMeetings", "Meeting ID: " + meeting.getId());
            Log.d("viewMeetings", "Start Hour: " + meeting.getStartMeetingHour());
            Log.d("viewMeetings", "End Hour: " + meeting.getEndMeetingHour());
            Log.d("viewMeetings", "Place: " + meeting.getMeetingPlace());
            Log.d("viewMeetings", "Subject: " + meeting.getMeetingSubject());
            Log.d("viewMeetings", "Participants: " + meeting.getParticipants().toString());
            Log.d("viewMeetings", "-------------------");
        }

    }
}
