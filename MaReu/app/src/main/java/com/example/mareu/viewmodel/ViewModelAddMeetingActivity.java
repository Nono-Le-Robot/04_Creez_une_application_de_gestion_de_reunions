package com.example.mareu.viewmodel;

import com.example.mareu.data.api.InitApi;
import com.example.mareu.data.api.MeetingApiService;
import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewModelAddMeetingActivity {
    public static void addNewMeeting(String startHour, String endHour, String place , String subject, List<String> participants){
        MeetingApiService mApiService = InitApi.getMeetingApiService();
        List<Meeting> meetings = mApiService.getMeetings();
        Meeting meetingToAdd = new Meeting(meetings.size(), startHour, endHour, place, subject, new ArrayList<>(participants));
        mApiService.createMeeting(meetingToAdd);
    }
}
