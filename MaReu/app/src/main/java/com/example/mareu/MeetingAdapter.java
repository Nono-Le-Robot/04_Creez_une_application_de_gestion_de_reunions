package com.example.mareu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.model.Meeting;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingHolder> implements Filterable {

    private final Integer[] colors = {
            Color.parseColor("#FF6961"), // rouge clair
            Color.parseColor("#77DD77"), // vert clair
            Color.parseColor("#C23B22"), // rouge brun
            Color.parseColor("#F08080"), // rose clair
            Color.parseColor("#FFD700"), // or
            Color.parseColor("#9370DB"), // violet moyen
            Color.parseColor("#FFA07A"), // saumon clair
            Color.parseColor("#4682B4")  // bleu acier
    };

    private List<Integer> colorList = new ArrayList<>();

    public MeetingAdapter() {
        colorList.addAll(Arrays.asList(colors));
        Collections.shuffle(colorList);
    }
    private int colorIndex = 0;
    private  OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;

    }

    private List<Meeting> meetings = new ArrayList<>();
    private List<Meeting> meetingsFull = new ArrayList<>();

    private int searchForType;
    @NonNull
    @Override
    public MeetingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_item, parent, false);
        return new MeetingHolder(itemView, listener);

    }


    @Override
    public void onBindViewHolder(@NonNull MeetingHolder holder, int position) {
        int color = colorList.get(colorIndex);
        colorIndex = (colorIndex + 1) % colorList.size();

        holder.randomColorLine.setBackgroundColor(color);

        Meeting currentMeeting = meetings.get(position);
        long startMilli = currentMeeting.getStartMeetingHour();
        long endMilli = currentMeeting.getEndMeetingHour();

        Date start = new Date(startMilli);
        int startHeures = start.getHours();
        int startMinutes = start.getMinutes();

        Date end = new Date(endMilli);
        int endHeures = end.getHours();
        int endMinutes = end.getMinutes();

        String formatStartHour;
        String formatEndHour;

        formatEndHour = (endHeures < 10 ? "0" : "") + endHeures + ":" + (endMinutes < 10 ? "0" : "") + endMinutes;
        formatStartHour = (startHeures < 10 ? "0" : "") + startHeures + ":" + (startMinutes < 10 ? "0" : "") + startMinutes;


        holder.textViewMeetingHour.setText( formatStartHour + " - " + formatEndHour);
        holder.textViewMeetingSubject.setText(currentMeeting.getMeetingSubject());
        holder.textViewMeetingPlace.setText("Room " + currentMeeting.getMeetingPlace());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MeetingDetailActivity.class);
                intent.putExtra("meetingId", currentMeeting.getId());
                intent.putExtra("meetingStartHour", formatStartHour);
                intent.putExtra("meetingEndHour", formatEndHour);
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

    public Filter getFilter(){
        Log.d("filteredMeetings", filteredMeetings.toString());
        return filteredMeetings;
    }

    public void searchForType(int idType){
        // 0 : meeting subject
        // 1 : room number
        // 2 : participant
        // 3 : start hour
        // 4 : end hour
        this.searchForType = idType;
    }

    private Filter filteredMeetings = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Meeting> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(meetingsFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();


                for(Meeting meeting : meetingsFull){
                    String participants = meeting.getParticipants();
                    participants = participants.substring(1, participants.length() - 1);
                    String[] participantsEmails = participants.split(", ");

                    if(meeting.getMeetingSubject().toLowerCase().contains(filterPattern) && searchForType == 0){
                        filteredList.add(meeting);
                    }

                    if(meeting.getMeetingPlace().toLowerCase().contains(filterPattern) && searchForType == 1){
                        filteredList.add(meeting);
                    }

                    for (String email : participantsEmails) {
                        if(email.toLowerCase().contains(filterPattern) && searchForType == 2){
                            filteredList.add(meeting);
                        }
                    }

                    Date dateStart = new Date(meeting.getStartMeetingHour());
                    int startHour = dateStart.getHours();
                    int startMinutes = dateStart.getMinutes();
                    String starthourToCompare = "";
                    if (startHour < 10) {
                        if(startMinutes < 10) starthourToCompare = '0' + String.valueOf(startHour) + ":" + "0" + String.valueOf(startMinutes);
                        else starthourToCompare = '0' + String.valueOf(startHour) + ":" + String.valueOf(startMinutes);
                    }
                    else{
                        if(startMinutes < 10) starthourToCompare = String.valueOf(startHour) + ":" + "0" + String.valueOf(startMinutes);
                        else starthourToCompare = String.valueOf(startHour) + ":" + String.valueOf(startMinutes);
                    }
                    if(starthourToCompare.contains(filterPattern) && searchForType == 3){
                        filteredList.add(meeting);
                    }

                    Date dateEnd = new Date(meeting.getEndMeetingHour());
                    int endHour = dateEnd.getHours();
                    int endMinutes = dateEnd.getMinutes();
                    String endHourToCompare = "";
                    if (endHour < 10) {
                        if(endMinutes < 10) endHourToCompare = '0' + String.valueOf(endHour) + ":" + "0" + String.valueOf(endMinutes);
                        else endHourToCompare = '0' + String.valueOf(endHour) + ":" + String.valueOf(endMinutes);
                    }
                    else{
                        if(endMinutes < 10) endHourToCompare = String.valueOf(endHour) + ":" + "0" + String.valueOf(endMinutes);
                        else endHourToCompare = String.valueOf(endHour) + ":" + String.valueOf(endMinutes);
                    }
                    if(endHourToCompare.contains(filterPattern) && searchForType == 4){
                        filteredList.add(meeting);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            meetings.clear();
            meetings.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void setMeetings(List<Meeting> meetings){
        this.meetings = meetings;
        this.meetingsFull = new ArrayList<>(meetings);
        notifyDataSetChanged();

    }

    public Meeting getMeetingAt(int position){
        return meetings.get(position);
    }

    class MeetingHolder extends RecyclerView.ViewHolder{
        private TextView textViewMeetingHour;
        private TextView textViewMeetingSubject;
        private TextView textViewMeetingPlace;
        private View randomColorLine;
        private FloatingActionButton deleteBtn;


        public MeetingHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textViewMeetingHour = itemView.findViewById(R.id.text_view_start_meeting_hour);
            textViewMeetingSubject = itemView.findViewById(R.id.text_view_meeting_subject);
            textViewMeetingPlace = itemView.findViewById(R.id.text_view_meeting_place);
            randomColorLine = itemView.findViewById(R.id.random_color);
            deleteBtn = itemView.findViewById(R.id.deleteIcon);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
