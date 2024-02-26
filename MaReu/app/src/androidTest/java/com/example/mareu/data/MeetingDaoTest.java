package com.example.mareu.data;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.mareu.model.Meeting;
import com.example.mareu.utils.LiveDataTestUtils;
import com.google.common.truth.Truth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;


public class MeetingDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private MeetingDatabase db;
    private MeetingDao meetingDao;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, MeetingDatabase.class).allowMainThreadQueries().build();
        meetingDao = db.meetingDao();
    }
    @Test
    public void testGetAllMeetings() {
        Meeting meeting = new Meeting(346345645,34435345,"444","Marketing", new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString()); // Initialize your Meeting object
        long id =  meetingDao.insert(meeting);

        LiveDataTestUtils.observeForTesting(meetingDao.getAllMeetings(), meetings -> {
            Truth.assertThat(meetings.size()).isEqualTo(1);
            Truth.assertThat(meetings.get(0).getId()).isEqualTo(id);
        });
    }
    @After
    public void tearDown() {
        db.clearAllTables();
        db.close();
    }

    // Vos méthodes de test vont  ici
}