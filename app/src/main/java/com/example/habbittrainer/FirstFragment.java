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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbittrainer.databinding.FragmentFirstBinding;
import com.example.habbittrainer.models.Hobby;
import com.example.habbittrainer.models.HobbyActivity;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    int indexForEditFrag;
    List<Hobby> hobbies = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        FirstFragmentArgs args = FirstFragmentArgs.fromBundle(getArguments());
        if(args != null)
            if(args.getHobby()!=null) {
//                hobbies.remove(indexForEditFrag);
                hobbies.add(indexForEditFrag, args.getHobby());
            }
        //Stores the activities list
        List<HobbyActivity> newActivities = new ArrayList<>();
        long milli = 123456789999l;
        java.sql.Time time = new java.sql.Time(milli);
        Hobby h = new Hobby("name", time, new boolean[7], true);
        newActivities.add(new HobbyActivity("Activity1", time, 1, time, time));
        h.setHobbyActivities(newActivities);
        hobbies.add(h);

        binding.HobbyListView.setAdapter(new HobbyListAdaptor(hobbies));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.HobbyListView.setLayoutManager(layoutManager);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                indexForEditFrag = 0;
                Navigation.findNavController(view).
                        navigate(FirstFragmentDirections.actionFirstFragmentToEditFragment()
                                .setHobby(hobbies.get(indexForEditFrag)));

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.HobbyListView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}