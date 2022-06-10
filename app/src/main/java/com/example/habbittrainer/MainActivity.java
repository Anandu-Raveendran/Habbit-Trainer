package com.example.habbittrainer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.habbittrainer.databinding.ActivityMainBinding;
import com.example.habbittrainer.models.Hobby;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    //This hobbylist is a global Structure and will be referenced from multiple frags.
    public static List<Hobby> hobbyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    protected void onStop() {
        super.onStop();
        DataSource.write(getApplicationContext(), hobbyList);
    }
}