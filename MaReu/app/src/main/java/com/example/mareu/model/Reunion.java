package com.example.mareu.model;

import android.icu.text.DateFormat;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Model object representing reunion
 */

public class Reunion {
        /** Identifier */
        private long id;
        /** Start hour of meeting */
        private DateFormat.HourCycle startMeetingHour;
        /** Start hour of meeting */
        private DateFormat.HourCycle endMeetingHour;
        /** Place of meeting */
        private String meetingPlace;
        /** Subject of meeting */
        private String meetingSubject;
        /** List of all participants (email) */
        private ArrayList<String> participants;

        public Reunion(long id, DateFormat.HourCycle startMeetingHour, DateFormat.HourCycle endMeetingHour, String meetingPlace, String meetingSubject, ArrayList<String> participants) {
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

        public DateFormat.HourCycle getStartMeetingHour() {
            return startMeetingHour;
        }

        public void setStartMeetingHour(DateFormat.HourCycle meetingHour) {
            this.startMeetingHour = startMeetingHour;
        }

        public DateFormat.HourCycle getEndMeetingHour() {
        return endMeetingHour;
    }

        public void setEndMeetingHour(DateFormat.HourCycle endMeetingHour) {
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
            com.example.mareu.model.Reunion reunion = (com.example.mareu.model.Reunion) o;
            return Objects.equals(id, reunion.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
}

