package com.example.habbittrainer;

import android.os.Bundle;
import android.util.Log;
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

public class HobbyListFragment extends Fragment implements ListItemCallbackContract{

    private FragmentFirstBinding binding;

    int indexForEditFrag = 0;
    List<Hobby> hobbies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        //Stores the activities list
//        List<HobbyActivity> newActivities = new ArrayList<>();
//        long milli = 123456789999l;
//        java.sql.Time time = new java.sql.Time(milli);
//        Hobby h = new Hobby("name", time, new boolean[7], true);
//        newActivities.add(new HobbyActivity("Activity1", time, 1, time, time));
//        h.setHobbyActivities(newActivities);

        hobbies = DataSource.readHobbies(getContext());
        if(hobbies == null) {
            Log.i("Anandu","data source returned null");
            hobbies = new ArrayList<>();
        } else
            Log.i("Anandu","Data source list is not null");
        MainActivity.hobbyList = hobbies;

        binding.HobbyListView.setAdapter(new HobbyListAdaptor(hobbies, this));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.HobbyListView.setLayoutManager(layoutManager);

        HobbyListFragmentArgs args = HobbyListFragmentArgs.fromBundle(getArguments());
        if(args != null)
            if(args.getHobby() != null) {
                //If editIndex is same as hobby size its cause this is a newly created object not yet in list
                if((args.getEditIndex() != hobbies.size()) && (hobbies.size() > 0)) {
                    hobbies.remove(args.getEditIndex());
                }
                hobbies.add(indexForEditFrag, args.getHobby());
                DataSource.write(getContext(),hobbies);
                binding.HobbyListView.getAdapter().notifyDataSetChanged();
            }

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Edit index is set to hobby size so that a new object is created.
                Navigation.findNavController(view).
                        navigate(HobbyListFragmentDirections.actionFirstFragmentToEditFragment()
                                .setHobby(new Hobby()).setEditIndex(hobbies.size()));

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

    @Override
    //Hobby list item callback
    public void listItemClickCallback(View v, int position) {
        //Edit index is set to hobby size so that a new object is created.
        Navigation.findNavController(v).
                navigate(HobbyListFragmentDirections.actionFirstFragmentToEditFragment()
                        .setHobby(hobbies.get(position)).setEditIndex(position));
    }

    @Override
    public void deleteItem(View v, Integer tag) {
        hobbies.remove(tag);
        MainActivity.hobbyList.remove(tag);
        binding.HobbyListView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void playItem(View v, Integer tag) {
        Navigation.findNavController(v).
                navigate(HobbyListFragmentDirections.actionFirstFragmentToRunFragment()
                        .setHobby(hobbies.get(tag)));
    }
}