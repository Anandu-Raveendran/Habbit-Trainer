package com.example.habbittrainer.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbittrainer.Adaptors.ActivityListAdaptor;
import com.example.habbittrainer.MyTimeTextWatcher;
import com.example.habbittrainer.databinding.EditActivityPopupBinding;
import com.example.habbittrainer.databinding.EditFragmentBinding;
import com.example.habbittrainer.interfaces.ListItemCallbackContract;
import com.example.habbittrainer.models.Days;
import com.example.habbittrainer.models.EditViewModel;
import com.example.habbittrainer.models.Hobby;
import com.example.habbittrainer.models.HobbyActivity;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class EditFragment extends Fragment implements ListItemCallbackContract {

    private EditViewModel mViewModel;
    private Hobby hobby;
    private EditFragmentBinding binding;
    private int edit_index = -1;
    private ViewGroup parent;
    private ActivityListAdaptor adaptor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = EditFragmentBinding.inflate(inflater);
        View view = binding.getRoot();
        parent = container;
        EditFragmentArgs args = EditFragmentArgs.fromBundle(getArguments());
        hobby = args.getHobby();
        edit_index = args.getEditIndex();
        Log.i("ANANDU", hobby.toString());
        if (hobby == null) {
            hobby = new Hobby();
        } else {
            binding.routineNameTextView.setText(hobby.getName());
            binding.scheduledTimeTextView.setText(hobby.getScheduledTime().toString());
            binding.sunday.setChecked(hobby.getDays()[Days.SUNDAY.getIntValue()]);
            binding.monday.setChecked(hobby.getDays()[Days.MONDAY.getIntValue()]);
            binding.tuesday.setChecked(hobby.getDays()[Days.TUESDAY.getIntValue()]);
            binding.wednesday.setChecked(hobby.getDays()[Days.WEDNESDAY.getIntValue()]);
            binding.thursday.setChecked(hobby.getDays()[Days.THURSDAY.getIntValue()]);
            binding.friday.setChecked(hobby.getDays()[Days.FRIDAY.getIntValue()]);
            binding.saturday.setChecked(hobby.getDays()[Days.SATURDAY.getIntValue()]);
            binding.reminderSwitch.setChecked(hobby.isEnableReminder());
        }
        mViewModel = new ViewModelProvider(this).get(EditViewModel.class);
        mViewModel.setHobby(hobby);

        adaptor = new ActivityListAdaptor(getContext(), hobby.getHobbyActivities(), this);
        binding.activitiesListView.setAdapter(adaptor);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.activitiesListView.setLayoutManager(layoutManager);

        //set the time formatter on text input
        binding.scheduledTimeTextView.addTextChangedListener(new MyTimeTextWatcher());
        binding.addActivityBtn.setOnClickListener(v -> {
            initActivityDialogBox(hobby, -1);
        });

        binding.saveBtn.setOnClickListener(v -> {
            String toastMessage = "";

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
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                    if (binding.routineNameTextView.getText().toString().isEmpty()) {
                        toastMessage = "Please add name to Hobby";
                    } else if (!MyTimeTextWatcher.isValidTime(binding.scheduledTimeTextView.getText().toString())) {
                        toastMessage = "Invalid Schedule time";
                    } else {
                        hobby.setName(binding.routineNameTextView.getText().toString());
                        hobby.setScheduledTime(new Time(sdf.parse(binding.scheduledTimeTextView.getText().toString()).getTime()));
                        hobby.setDays(days);
                        hobby.setEnableReminder(binding.reminderSwitch.isChecked());
                        mViewModel.setHobby(hobby);
                        toastMessage = "Data edit complete";

                        Navigation.findNavController(view)
                                .navigate(EditFragmentDirections.actionEditFragmentToFirstFragment().setHobby(hobby)
                                        .setEditIndex(edit_index));
                    }
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
        binding.playBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hobby.getHobbyActivities().isEmpty()) {
                    Toast.makeText(getContext(), "Activity list is empty.", Toast.LENGTH_SHORT).show();
                    Log.i("Anandu", "Play button pressed. Activity list is empty");
                } else {
                    Navigation.findNavController(view)
                            .navigate(EditFragmentDirections.actionEditFragmentToRunFragment().setHobby(hobby));
                }
            }
        });


        binding.reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.shedulelayout.setVisibility(View.VISIBLE);
                } else {
                    binding.shedulelayout.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    private void initActivityDialogBox(Hobby hobby, int activtyIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        EditActivityPopupBinding dialogBinding = EditActivityPopupBinding.inflate(layoutInflater, parent, false);
        builder.setView(dialogBinding.getRoot());
        AlertDialog dialog = builder.create();

        if (activtyIndex != -1) {
            dialogBinding.activityNameTextView.setText(hobby.getHobbyActivities().get(activtyIndex).getName());
            dialogBinding.breaksTextView.setText(hobby.getHobbyActivities().get(activtyIndex).getBreakLength().toString());
            dialogBinding.breakAfterText.setText(hobby.getHobbyActivities().get(activtyIndex).getBreakAfterActivity().toString());
            dialogBinding.repetitionTextView.setText(String.valueOf(hobby.getHobbyActivities().get(activtyIndex).getRepetitions()));
            dialogBinding.activityTimehourTextView.setText(String.valueOf(hobby.getHobbyActivities().get(activtyIndex).getTimeNeeded().getHour()));
            dialogBinding.activityTimeMinsTextView.setText(String.valueOf(hobby.getHobbyActivities().get(activtyIndex).getTimeNeeded().getMinute()));
            dialogBinding.activityTimeSecTextView.setText(String.valueOf(hobby.getHobbyActivities().get(activtyIndex).getTimeNeeded().getSecond()));
        }

        dialogBinding.saveactivitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toastMessage = "";

                //Null checks
                if (dialogBinding.activityTimehourTextView.getText().toString().isEmpty()) {
                    dialogBinding.activityTimehourTextView.setText("00");
                }
                if (dialogBinding.activityTimeMinsTextView.getText().toString().isEmpty()) {
                    dialogBinding.activityTimeMinsTextView.setText("00");
                }
                if (dialogBinding.activityTimeSecTextView.getText().toString().isEmpty()) {
                    dialogBinding.activityTimeSecTextView.setText("00");
                }
                if (dialogBinding.breakAfterText.getText().toString().isEmpty()) {
                    dialogBinding.breakAfterText.setText("00");
                }
                if (dialogBinding.breaksTextView.getText().toString().isEmpty()) {
                    dialogBinding.breaksTextView.setText("00");
                }
                if (dialogBinding.repetitionTextView.getText().toString().isEmpty()) {
                    dialogBinding.repetitionTextView.setText("1");
                }

                //sanity check
                if (dialogBinding.activityNameTextView.getText().toString().isEmpty()) {
                    toastMessage += "Add a name to Activity";
                } else if (!validHour(dialogBinding.activityTimehourTextView.getText().toString())) {
                    toastMessage += "Invalid time duration hours";
                } else if (!validMin(dialogBinding.activityTimeMinsTextView.getText().toString())) {
                    toastMessage += "Invalid time duration mins";
                } else if (!validMin(dialogBinding.activityTimeSecTextView.getText().toString())) {
                    toastMessage += "Invalid time duration seconds";
                } else if (!validMin(dialogBinding.breakAfterText.getText().toString())) {
                    toastMessage += "Invalid time after activity";
                } else if (!validMin(dialogBinding.breaksTextView.getText().toString())) {
                    toastMessage += "Invalid time break in between reps";
                } else if (Integer.parseInt(dialogBinding.repetitionTextView.getText().toString()) < 0) {
                    toastMessage += "Invalid repetitions";
                } else {
                    HobbyActivity activity = new HobbyActivity(
                            dialogBinding.activityNameTextView.getText().toString(),
                            LocalTime.of(Integer.parseInt(dialogBinding.activityTimehourTextView.getText().toString()),
                                    Integer.parseInt(dialogBinding.activityTimeMinsTextView.getText().toString())),
                            Integer.parseInt(dialogBinding.repetitionTextView.getText().toString()),
                            LocalTime.of(0, Integer.parseInt(dialogBinding.breaksTextView.getText().toString())),
                            LocalTime.of(0, Integer.parseInt(dialogBinding.breakAfterText.getText().toString())));
                    if (activtyIndex == -1) {   // Add new activity case (insert)
                        hobby.getHobbyActivities().add(activity);
                    } else {   // update case (not new activity)
                        hobby.getHobbyActivities().remove(activtyIndex);
                        hobby.getHobbyActivities().add(activtyIndex, activity);
                    }
                }
                if (!toastMessage.isEmpty()) { // error case
                    Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Activity saved successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss(); // success case
                }
                Log.i("Anandu", "dialog saveBtn " + toastMessage);
                binding.activitiesListView.getAdapter().notifyDataSetChanged();
            }
        });

        dialogBinding.cancelActivitySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

//        dialogBinding.activityTimeTextView.addTextChangedListener(new MyTimeTextWatcher());
//        dialogBinding.breakAfterText.addTextChangedListener(new MyTimeTextWatcher());
//        dialogBinding.breaksTextView.addTextChangedListener(new MyTimeTextWatcher());

        dialog.show();

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

    @Override
    public void onResume() {
        super.onResume();
        adaptor.notifyDataSetChanged();
    }

    @Override
    public void listItemClickCallback(View v, int position) {
        initActivityDialogBox(hobby, position);
    }

    @Override
    public void deleteItem(View v, Integer tag) {
        hobby.getHobbyActivities().remove((int) tag);
        Log.i("Anandu", "Deleted item at index " + tag);
        binding.activitiesListView.getAdapter().notifyDataSetChanged();
    }

    //There is no play for activities so need not implement this method.
    @Override
    public void playItem(View v, Integer tag) {
    }


    private boolean validHour(String hourStr) {
        int hour = Integer.parseInt(hourStr);
        return (hour >= 0 && hour < 24);
    }

    private boolean validMin(String minStr) {
        int min = Integer.parseInt(minStr);
        return (min >= 0 && min < 60);
    }
}