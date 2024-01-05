package com.example.mareu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mareu.data.MeetingViewModel;
import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.model.Meeting;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_MEETING_REQUEST = 1;
    private MeetingViewModel meetingViewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FloatingActionButton buttonAddMeeting = findViewById(R.id.btn_add_meeting);
        buttonAddMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMeeting.class);
                startActivityForResult(intent, ADD_MEETING_REQUEST);
            }
        });

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Souhaitez-vous vraiment quitter l'application ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish(); // Ferme l'activité (et l'application si c'est la seule activité)
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss(); // Ferme la boîte de dialogue
                            }
                        });
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                meetingViewModel.delete(adapter.getMeetingAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Meeting deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_MEETING_REQUEST && resultCode == RESULT_OK){

            String subject = data.getStringExtra(AddMeeting.EXTRA_SUBJECT);
            String place = data.getStringExtra(AddMeeting.EXTRA_PLACE);
            String startHour = data.getStringExtra(AddMeeting.EXTRA_START_HOUR);
            String endHour = data.getStringExtra(AddMeeting.EXTRA_END_HOUR);

            Meeting meeting = new Meeting(startHour,endHour,place,subject,new ArrayList<>(Arrays.asList("test@gmail.com","test2@gmail.com","test3@gmail.com")).toString());
            meetingViewModel.insert(meeting);

            Toast.makeText(this, "Meeting saved", Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(this, "Error saving meeting", Toast.LENGTH_SHORT).show();
        }
    }


}
