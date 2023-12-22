package com.example.mareu.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mareu.model.Meeting;

import java.util.List;

public class MeetingRepository {
    private MeetingDao meetingDao;
    private LiveData<List<Meeting>> allMeetings;
    public MeetingRepository(Application application){
        MeetingDatabase database = MeetingDatabase.getInstance(application);
        meetingDao = database.meetingDao();
        allMeetings = meetingDao.getAllMeetings();
    }

    public  void  insert(Meeting meeting){
        new InsertMeetingAsyncTask(meetingDao).execute(meeting);

    }
    public void update(Meeting meeting){
        new UpdateMeetingAsyncTask(meetingDao).execute(meeting);

    }

    public void delete(Meeting meeting){
        new DeleteMeetingAsyncTask(meetingDao).execute(meeting);

    }
    public void deleteAllMeetings(){
        new DeleteAllMeetingsAsyncTask(meetingDao).execute();

    }

    public LiveData<List<Meeting>> getAllMeetings() {
        return allMeetings;
    }

    private static class InsertMeetingAsyncTask extends AsyncTask<Meeting, Void, Void> {
        private  MeetingDao meetingDao;
        private InsertMeetingAsyncTask(MeetingDao meetingDao){
            this.meetingDao = meetingDao;
        }
        @Override
        protected Void doInBackground(Meeting... meetings) {
            meetingDao.insert(meetings[0]);
            return null;
        }
    }

    private static class UpdateMeetingAsyncTask extends AsyncTask<Meeting, Void, Void> {
        private  MeetingDao meetingDao;
        private UpdateMeetingAsyncTask(MeetingDao meetingDao){
            this.meetingDao = meetingDao;
        }
        @Override
        protected Void doInBackground(Meeting... meetings) {
            meetingDao.update(meetings[0]);
            return null;
        }
    }

    private static class DeleteMeetingAsyncTask extends AsyncTask<Meeting, Void, Void> {
        private  MeetingDao meetingDao;
        private DeleteMeetingAsyncTask(MeetingDao meetingDao){
            this.meetingDao = meetingDao;
        }
        @Override
        protected Void doInBackground(Meeting... meetings) {
            meetingDao.delete(meetings[0]);
            return null;
        }
    }

    private static class DeleteAllMeetingsAsyncTask extends AsyncTask<Void, Void, Void> {
        private  MeetingDao meetingDao;
        private DeleteAllMeetingsAsyncTask(MeetingDao meetingDao){
            this.meetingDao = meetingDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            meetingDao.deleteAllMeetings();
            return null;
        }
    }
}
