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
            meetingDao.insert(new Meeting( 346345645,34435345,"444","Marketing", new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString()));
            meetingDao.insert(new Meeting( 435634545,345634456,"37","Weekly standup", new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString()));
            meetingDao.insert(new Meeting( 456363456,456363456,"99","Review code", new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString()));
            meetingDao.insert(new Meeting( 344563456,343456345, "669","Talk about buisiness", new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString()));
            return null;
        }
    }
}
