package com.example.mareu.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mareu.data.api.MeetingApiService;
import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.data.api.InitApi;
import com.example.mareu.model.Meeting;
import com.example.mareu.viewmodel.ViewModelMainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setText("+");
        // log all meetings, add and remove meeting test
        ViewModelMainActivity.processData();
    }
}
