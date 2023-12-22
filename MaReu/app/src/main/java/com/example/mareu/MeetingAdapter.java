package com.example.mareu;

import android.content.Context;
import android.content.Intent;
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

        holder.textViewMeetingHour.setText(currentMeeting.getStartMeetingHour() + " - " + currentMeeting.getEndMeetingHour());
        holder.textViewMeetingSubject.setText(currentMeeting.getMeetingSubject());
        holder.textViewMeetingPlace.setText(currentMeeting.getMeetingPlace());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MeetingDetailActivity.class);
                intent.putExtra("meetingId", currentMeeting.getId());
                intent.putExtra("meetingStartHour", currentMeeting.getStartMeetingHour());
                intent.putExtra("meetingEndHour", currentMeeting.getEndMeetingHour());
                intent.putExtra("meetingPlace", currentMeeting.getMeetingPlace());
                intent.putExtra("meetingSubject", currentMeeting.getMeetingSubject());
                intent.putExtra("meetingParticipants", currentMeeting.getParticipants());
                v.getContext().startActivity(intent);
            }
        });
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
        private TextView textViewMeetingHour;
        private TextView textViewMeetingSubject;
        private TextView textViewMeetingPlace;


        public MeetingHolder(@NonNull View itemView) {
            super(itemView);
            textViewMeetingHour = itemView.findViewById(R.id.text_view_start_meeting_hour);
            textViewMeetingSubject = itemView.findViewById(R.id.text_view_meeting_subject);
            textViewMeetingPlace = itemView.findViewById(R.id.text_view_meeting_place);
        }
    }
}
