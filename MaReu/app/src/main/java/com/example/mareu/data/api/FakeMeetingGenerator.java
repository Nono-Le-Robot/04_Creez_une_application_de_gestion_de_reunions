package com.example.mareu.data.api;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FakeMeetingGenerator {
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(

            new Meeting(0, "14:00", "18:00", "Room 22",
                    "Talk about new features",  new ArrayList<>(Arrays.asList("sannier.renaud@gmail.com"))),

            new Meeting(1, "16:00", "17:00", "Room 44",
                    "Marketing",  new ArrayList<>(Arrays.asList("sannier.renaud@gmail.com", "jean.legrand@yahoo.fr"))),

            new Meeting(2, "09:00", "10:00", "Room 91",
                    "Weekly Stand up",  new ArrayList<>(Arrays.asList("sannier.renaud@gmail.com", "marcel.dupont@gmail.com")))

    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
