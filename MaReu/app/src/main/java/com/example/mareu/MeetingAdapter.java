package com.example.mareu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingHolder> {

    private List<Meeting> meetings = new ArrayList<>();
    @NonNull
    @Override
    public MeetingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_item, parent, false);
        return new MeetingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingHolder holder, int position) {
        Meeting currentMeeting = meetings.get(position);
        holder.textViewHour.setText(currentMeeting.getStartMeetingHour());
        holder.textViewSubject.setText(currentMeeting.getMeetingSubject());
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public void setMeetings(List<Meeting> meetings){
        this.meetings = meetings;
        notifyDataSetChanged();

    }

    class MeetingHolder extends RecyclerView.ViewHolder{
        private TextView textViewHour;
        private TextView textViewSubject;


        public MeetingHolder(@NonNull View itemView) {
            super(itemView);
            textViewHour = itemView.findViewById(R.id.text_view_start_meeting_hour);
            textViewSubject = itemView.findViewById(R.id.text_view_meeting_subject);
        }
    }
}
