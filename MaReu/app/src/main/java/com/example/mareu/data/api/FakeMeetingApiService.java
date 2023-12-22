package com.example.mareu.data.api;

import com.example.mareu.model.Meeting;

import java.util.List;

public class FakeMeetingApiService implements MeetingApiService {
    private List<Meeting> meetings = FakeMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public Meeting getMeeting(int position) {
        return meetings.get(position);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
}

