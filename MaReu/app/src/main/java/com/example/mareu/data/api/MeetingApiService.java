package com.example.mareu.data.api;

import com.example.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {
    List<Meeting> getMeetings();

    Meeting getMeeting(int position);

    void deleteMeeting(Meeting reunion);

    void createMeeting(Meeting reunion);
}
