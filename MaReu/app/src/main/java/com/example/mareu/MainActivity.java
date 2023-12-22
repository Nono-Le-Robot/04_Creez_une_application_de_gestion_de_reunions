package com.example.mareu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mareu.data.MeetingViewModel;
import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.model.Meeting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MeetingViewModel meetingViewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Souhaitez-vous vraiment quitter l'application ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // L'utilisateur a choisi de quitter l'application
                                finish(); // Ferme l'activité (et l'application si c'est la seule activité)
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // L'utilisateur a choisi de rester dans l'application
                                dialog.dismiss(); // Ferme la boîte de dialogue
                            }
                        });
                // Créer et afficher la boîte de dialogue
                builder.create().show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        MeetingAdapter adapter = new MeetingAdapter();
        recyclerView.setAdapter(adapter);

        meetingViewModel = new ViewModelProvider(this).get(MeetingViewModel.class);
        meetingViewModel.getAllMeetings().observe(this, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                Log.d("see_meetings", meetings.toString());
                Collections.sort(meetings, new Comparator<Meeting>() {
                    @Override
                    public int compare(Meeting meeting1, Meeting meeting2) {
                        return meeting1.getStartMeetingHour().compareTo(meeting2.getStartMeetingHour());
                    }
                });
                //update recyclerview
                adapter.setMeetings(meetings);
            }
        });
    }
}
