package com.example.mareu.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Model object representing meeting
 */
@Entity(tableName = "meeting_table")
public class Meeting {
    /** Identifier */
    @PrimaryKey(autoGenerate = true)
    private int id;
    /** Start hour of meeting */
    private long startMeetingHour;
    /** Start hour of meeting */
    private long endMeetingHour;
    /** Place of meeting */
    private String meetingPlace;
    /** Subject of meeting */
    private String meetingSubject;
    /** List of all participants (email) */
    private String participants;

    public Meeting( long startMeetingHour, long endMeetingHour, String meetingPlace, String meetingSubject, String participants) {

        this.startMeetingHour = startMeetingHour;
        this.endMeetingHour = endMeetingHour;
        this.meetingPlace = meetingPlace;
        this.meetingSubject = meetingSubject;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStartMeetingHour() {
        return startMeetingHour;
    }

    public void setStartMeetingHour(long meetingHour) {
        this.startMeetingHour = startMeetingHour;
    }

    public long getEndMeetingHour() {
        return endMeetingHour;
    }

    public void setEndMeetingHour(long endMeetingHour) {
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
        this.meetingSubject = meetingSubject;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
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

