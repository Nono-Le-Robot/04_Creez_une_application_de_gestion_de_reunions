package com.example.mareu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mareu.data.MeetingViewModel;
import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.model.Meeting;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_MEETING_REQUEST = 1;
    private MeetingViewModel meetingViewModel;

    private MeetingAdapter adapter;
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

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new MeetingAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MeetingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                meetingViewModel.delete(adapter.getMeetingAt(position));
                Toast.makeText(MainActivity.this, "Meeting deleted", Toast.LENGTH_SHORT).show();
            }
        });

        meetingViewModel = new ViewModelProvider(this).get(MeetingViewModel.class);
        meetingViewModel.getAllMeetings().observe(this, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                Log.d("see_meetings", meetings.toString());
                //Collections.sort(meetings, new Comparator<Meeting>() {
                  //  @Override
                  //  public int compare(Meeting meeting1, Meeting meeting2) {
                  //      return meeting1.getStartMeetingHour().compareTo(meeting2.getStartMeetingHour());
                    //}
                //});
                //update recyclerview
                adapter.setMeetings(meetings);

            }

            public MeetingAdapter test = adapter;
        });
    }

    public void goBack(View view) {
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

    public void searchFor(View view){
        showSearchForDialog();


    }

    private void showSearchForDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_search, null);

        Spinner spinnerSearchType = view.findViewById(R.id.spinner_search_type);
        EditText editTextSearch = view.findViewById(R.id.edit_text_searchFor);
        spinnerSearchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 1) editTextSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
                else editTextSearch.setInputType(InputType.TYPE_CLASS_TEXT);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        builder.setView(view);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Spinner selectedPosition = spinnerSearchType;

                String searchText = editTextSearch.getText().toString();

                if (TextUtils.isEmpty(searchText)) {
                    adapter.getFilter().filter("");
                    return;
                }

                if (selectedPosition.getSelectedItem().equals("Meeting Subject")) {
                    adapter.searchForType(0);
                    adapter.getFilter().filter(searchText);
                }

                if (selectedPosition.getSelectedItem().equals("Room Number")) {
                    adapter.searchForType(1);
                    adapter.getFilter().filter(searchText);
                }






            }
        });

        builder.setNegativeButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.getFilter().filter("");
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_MEETING_REQUEST && resultCode == RESULT_OK){

            String subject = data.getStringExtra(AddMeeting.EXTRA_SUBJECT);
            String place = data.getStringExtra(AddMeeting.EXTRA_PLACE);
            String startHour = data.getStringExtra(AddMeeting.EXTRA_START_HOUR);
            String endHour = data.getStringExtra(AddMeeting.EXTRA_END_HOUR);
            String participants = data.getStringExtra(AddMeeting.EXTRA_PARTICIPANTS);
            Log.d("participants", participants);


            Meeting meeting = new Meeting(Long.parseLong(startHour),Long.parseLong(endHour),place,subject,participants);
            meetingViewModel.insert(meeting);
        }

    }


}
