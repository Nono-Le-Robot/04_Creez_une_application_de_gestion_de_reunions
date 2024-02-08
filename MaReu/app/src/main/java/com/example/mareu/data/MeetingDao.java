package com.example.mareu.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mareu.model.Meeting;

import java.util.List;

@Dao
public interface MeetingDao {
    @Insert
    void insert(Meeting meeting);
    @Update
    void update(Meeting meeting);
    @Delete
    void delete(Meeting meeting);


    @Query("DELETE FROM meeting_table")
    void deleteAllMeetings();

    @Query("SELECT * FROM meeting_table")
    LiveData<List<Meeting>> getAllMeetings();
}
