package com.example.mareu.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;

@Database(entities = {Meeting.class} ,version = 1)
public abstract class MeetingDatabase extends RoomDatabase {
    private static MeetingDatabase instance;
    public abstract MeetingDao meetingDao();

    public static synchronized MeetingDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MeetingDatabase.class, "meeting_database")
                    .fallbackToDestructiveMigrationOnDowngrade() // Permet la migration vers des versions inférieures
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class  PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private MeetingDao meetingDao;

        private PopulateDbAsyncTask(MeetingDatabase db){
            meetingDao = db.meetingDao();
        }

        @Override
        protected  Void doInBackground(Void... voids){
            meetingDao.insert(new Meeting( "08:00","10:00","Room 911","Talk about new features", new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString()));
            meetingDao.insert(new Meeting( "10:00","11:00","Room 444","Marketing", new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString()));
            meetingDao.insert(new Meeting( "09:00","09:30","Room 37","Weekly standup", new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString()));
            meetingDao.insert(new Meeting( "11:00","12:00","Room 99","Review code", new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString()));
            meetingDao.insert(new Meeting( "16:00","17:00","Room 669","Talk about buisiness", new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString()));

            return null;
        }
    }
}