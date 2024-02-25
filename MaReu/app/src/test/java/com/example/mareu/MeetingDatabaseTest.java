package com.example.mareu;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.mareu.data.MeetingDao;
import com.example.mareu.data.MeetingDatabase;
import com.example.mareu.model.Meeting;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = Config.OLDEST_SDK)
public class MeetingDatabaseTest {
    private MeetingDatabase db;
    private MeetingDao meetingDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, MeetingDatabase.class).build();
        meetingDao = db.meetingDao();
    }
    @Test
    public void testGetAllMeetings() {
        // Insert test data
        Meeting meeting = new Meeting(346345645,34435345,"444","Marketing", new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString()); // Initialize your Meeting object
        meetingDao.insert(meeting);

        // Use Robolectric's scheduler to ensure operations are executed
        Robolectric.flushForegroundThreadScheduler();

        // Observe LiveData and perform actions within the observer
        meetingDao.getAllMeetings().observeForever(new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                if (meetings != null) {
                    // Perform actions with the retrieved meetings
                    System.out.println("WWWWWWWWWWWWWWWWWWOOOOOOOOOOOOOOOOOOOOOOOOOOOOOWWWWWWWWWWWWWWWW");
                    System.out.println("WWWWWWWWWWWWWWWWWWOOOOOOOOOOOOOOOOOOOOOOOOOOOOOWWWWWWWWWWWWWWWW");
                    System.out.println("WWWWWWWWWWWWWWWWWWOOOOOOOOOOOOOOOOOOOOOOOOOOOOOWWWWWWWWWWWWWWWW");
                    System.out.println("WWWWWWWWWWWWWWWWWWOOOOOOOOOOOOOOOOOOOOOOOOOOOOOWWWWWWWWWWWWWWWW");


                } else {
                    System.out.println("ERRRRRRRRRRRRRRRRRROOOOOOOOOOOOOOOOOOOOOOOOOORRRRRRRRRRRRRRRRRRRRRRRR");
                    System.out.println("ERRRRRRRRRRRRRRRRRROOOOOOOOOOOOOOOOOOOOOOOOOORRRRRRRRRRRRRRRRRRRRRRRR");
                    System.out.println("ERRRRRRRRRRRRRRRRRROOOOOOOOOOOOOOOOOOOOOOOOOORRRRRRRRRRRRRRRRRRRRRRRR");
                    System.out.println("ERRRRRRRRRRRRRRRRRROOOOOOOOOOOOOOOOOOOOOOOOOORRRRRRRRRRRRRRRRRRRRRRRR");
                }
            }
        });

        // Ensure that the observer is removed after the test to avoid memory leaks
        meetingDao.getAllMeetings();
    }
    @After
    public void closeDb() {
        db.close();
    }

    // Vos méthodes de test vont  ici
}