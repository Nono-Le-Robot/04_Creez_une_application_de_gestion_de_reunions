package com.example.mareu.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mareu.R;
import com.example.mareu.viewmodel.ViewModelAddMeetingActivity;

import java.util.Arrays;
import java.util.List;

public class AddMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        //récupérer ici les données des champs et les passer en paramètres ci dessous :
        ViewModelAddMeetingActivity.addNewMeeting("08:00", "11:00","Room 999","Test add meeting", Arrays.asList("test@test.fr", "test2@test.fr", "test3@test.fr"));
    }
}