package com.example.mareu.model;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Model object representing meeting
 */

public class Meeting {
    /** Identifier */
    private long id;
    /** Start hour of meeting */
    private String startMeetingHour;
    /** Start hour of meeting */
    private String endMeetingHour;
    /** Place of meeting */
    private String meetingPlace;
    /** Subject of meeting */
    private String meetingSubject;
    /** List of all participants (email) */
    private ArrayList<String> participants;

    public Meeting(long id, String startMeetingHour, String endMeetingHour, String meetingPlace, String meetingSubject, ArrayList<String> participants) {
        this.id = id;
        this.startMeetingHour = startMeetingHour;
        this.endMeetingHour = endMeetingHour;
        this.meetingPlace = meetingPlace;
        this.meetingSubject = meetingSubject;
        this.participants = participants;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartMeetingHour() {
        return startMeetingHour;
    }

    public void setStartMeetingHour(String meetingHour) {
        this.startMeetingHour = startMeetingHour;
    }

    public String getEndMeetingHour() {
        return endMeetingHour;
    }

    public void setEndMeetingHour(String endMeetingHour) {
        this.endMeetingHour = endMeetingHour;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingPlace = meetingSubject;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.example.mareu.model.Meeting meeting = (com.example.mareu.model.Meeting) o;
        return Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

