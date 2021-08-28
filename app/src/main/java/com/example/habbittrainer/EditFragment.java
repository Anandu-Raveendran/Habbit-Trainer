package com.example.habbittrainer;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavArgs;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.habbittrainer.databinding.AddHobbyActivityBinding;
import com.example.habbittrainer.databinding.EditFragmentBinding;
import com.example.habbittrainer.models.Days;
import com.example.habbittrainer.models.EditViewModel;
import com.example.habbittrainer.models.Hobby;
import com.example.habbittrainer.models.HobbyActivity;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EditFragment extends Fragment {

    public static EditFragment newInstance() {
        return new EditFragment();
    }

    private EditViewModel mViewModel;
    Hobby hobby;
    private EditFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = EditFragmentBinding.inflate(inflater);
        View view = binding.getRoot();

        EditFragmentArgs args = EditFragmentArgs.fromBundle(getArguments());
        hobby = args.getHobby();
        Log.i("ANANDU", hobby.toString());
        if (hobby == null)
            hobby = new Hobby();
        mViewModel = new ViewModelProvider(this).get(EditViewModel.class);

        mViewModel.setHobby(hobby);

        binding.activitiesListView.setAdapter(new ActivityListAdaptor(hobby.getHobbyActivities()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.activitiesListView.setLayoutManager(layoutManager);

        //set the time formatter on text input
        binding.scheduledTimeTextView.addTextChangedListener(new MyTimeTextWatcher());
        binding.addActivityBtn.setOnClickListener(v -> {
            initActivityDialogBox(container, hobby);
        });

        binding.saveBtn.setOnClickListener(v -> {
            String toastMessage = "";
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

            if (binding.routineNameTextView.getText().toString().isEmpty()) {
                toastMessage += "Routine's name cant be empty";
            } else if (binding.scheduledTimeTextView.getText().toString().isEmpty()) {
                toastMessage += "Scheduled time cant be empty";
            } else {
                boolean[] days = new boolean[7];
                days[Days.SUNDAY.getIntValue()] = binding.sunday.isChecked();
                days[Days.MONDAY.getIntValue()] = binding.monday.isChecked();
                days[Days.TUESDAY.getIntValue()] = binding.tuesday.isChecked();
                days[Days.WEDNESDAY.getIntValue()] = binding.wednesday.isChecked();
                days[Days.THURSDAY.getIntValue()] = binding.thursday.isChecked();
                days[Days.FRIDAY.getIntValue()] = binding.friday.isChecked();
                days[Days.SATURDAY.getIntValue()] = binding.saturday.isChecked();


                try {
                    hobby.setName(binding.routineNameTextView.getText().toString());
                    hobby.setScheduledTime(new Time(sdf.parse(binding.scheduledTimeTextView.getText().toString()).getTime()));
                    hobby.setDays(days);
                    hobby.setEnableReminder(binding.reminderSwitch.isActivated());
                    mViewModel.setHobby(hobby);
                    toastMessage = "Data saved successfully";

                    Navigation.findNavController(view)
                            .navigate(EditFragmentDirections.actionEditFragmentToFirstFragment().setHobby(hobby));

                } catch (ParseException e) {
                    toastMessage = "!!! Data Error: data not saved !!!";
                    e.printStackTrace();
                }
            }
            Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();

            Log.i("ANANDU", mViewModel.getHobby().getValue().toString());
        });
        binding.cancelBtn.setOnClickListener(v ->
                Navigation.findNavController(view)
                        .navigate(EditFragmentDirections.actionEditFragmentToFirstFragment()));


        return view;
    }

    private void initActivityDialogBox(ViewGroup container, Hobby hobby) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        AddHobbyActivityBinding dialogBinding = AddHobbyActivityBinding.inflate(layoutInflater, container, false);
        builder.setView(dialogBinding.getRoot());
        builder.setPositiveButton("Save", (dialog, which) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

            try {
                hobby.getHobbyActivities().add(new HobbyActivity(
                        dialogBinding.activityNameTextView.getText().toString(),
                        new Time(sdf.parse(dialogBinding.activityTimeTextView.getText().toString()).getTime()),
                        Integer.parseInt(dialogBinding.repetitionTextView.getText().toString()),
                        new Time(sdf.parse(dialogBinding.breaksTextView.getText().toString()).getTime()),
                        new Time(sdf.parse(dialogBinding.breakAfterText.getText().toString()).getTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            binding.activitiesListView.getAdapter().notifyDataSetChanged();
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBinding.activityTimeTextView.addTextChangedListener(new MyTimeTextWatcher());
        dialogBinding.breakAfterText.addTextChangedListener(new MyTimeTextWatcher());
        dialogBinding.breaksTextView.addTextChangedListener(new MyTimeTextWatcher());

        builder.create();
        builder.show();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}