package com.example.habbittrainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.habbittrainer.databinding.FragmentFirstBinding;
import com.example.habbittrainer.models.Hobby;
import com.example.habbittrainer.models.HobbyActivity;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    public static FirstFragment newInstanc() {return new FirstFragment();}

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Stores the activities list
                List<HobbyActivity> newActivities = new ArrayList<>();
                long milli = 123456789999l;
                java.sql.Time time = new java.sql.Time(milli);
                Hobby h = new Hobby("name", time, 2, true);
                newActivities.add(new HobbyActivity("Activity1", time, 1, time, time));
                h.setHobbyActivities(newActivities);

                Navigation.findNavController(view).
                        navigate(FirstFragmentDirections.actionFirstFragmentToEditFragment()
                                .setHobby(h));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}