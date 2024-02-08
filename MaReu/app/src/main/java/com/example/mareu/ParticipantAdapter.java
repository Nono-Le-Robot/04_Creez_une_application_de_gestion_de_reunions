package com.example.mareu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder> {

    private Context context;
    private List<String> participantsList;

    public ParticipantAdapter(Context context, List<String> participantsList) {
        this.context = context;
        this.participantsList = participantsList;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(com.example.mareu.R.layout.participant_item, parent, false);
        return new ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        String participant = participantsList.get(position);
        holder.textViewParticipant.setText(participant);
    }

    @Override
    public int getItemCount() {
        return participantsList.size();
    }

    public static class ParticipantViewHolder extends RecyclerView.ViewHolder {
        TextView textViewParticipant;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewParticipant = itemView.findViewById(R.id.textViewParticipant);
        }
    }
}
