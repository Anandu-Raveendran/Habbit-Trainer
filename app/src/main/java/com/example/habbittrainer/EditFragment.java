package com.example.habbittrainer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbittrainer.databinding.AddHobbyActivityBinding;
import com.example.habbittrainer.databinding.EditFragmentBinding;
import com.example.habbittrainer.models.Days;
import com.example.habbittrainer.models.EditViewModel;
import com.example.habbittrainer.models.Hobby;
import com.example.habbittrainer.models.HobbyActivity;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditFragment extends Fragment implements ListItemCallbackContract {

    private EditViewModel mViewModel;
    private Hobby hobby;
    private EditFragmentBinding binding;
    private int edit_index=-1;
    private ViewGroup parent;

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
        } else{
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

        binding.activitiesListView.setAdapter(new ActivityListAdaptor(hobby.getHobbyActivities(),this));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.activitiesListView.setLayoutManager(layoutManager);

        //set the time formatter on text input
        binding.scheduledTimeTextView.addTextChangedListener(new MyTimeTextWatcher());
        binding.addActivityBtn.setOnClickListener(v -> {
            initActivityDialogBox(hobby,-1);
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
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                    hobby.setName(binding.routineNameTextView.getText().toString());
                    if(MyTimeTextWatcher.isValidTime(binding.scheduledTimeTextView.getText().toString())) {
                        hobby.setScheduledTime(new Time(sdf.parse(binding.scheduledTimeTextView.getText().toString()).getTime()));
                        hobby.setDays(days);
                        hobby.setEnableReminder(binding.reminderSwitch.isChecked());
                        mViewModel.setHobby(hobby);
                        toastMessage = "Data edit complete";

                        Navigation.findNavController(view)
                                .navigate(EditFragmentDirections.actionEditFragmentToFirstFragment().setHobby(hobby)
                                        .setEditIndex(edit_index));
                    } else {
                        toastMessage = "Invalid Schedule time";
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


        return view;
    }

    private void initActivityDialogBox(Hobby hobby, int activtyIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        AddHobbyActivityBinding dialogBinding = AddHobbyActivityBinding.inflate(layoutInflater, parent, false);
        builder.setView(dialogBinding.getRoot());

        if(activtyIndex != -1) {
            dialogBinding.activityNameTextView.setText(hobby.getHobbyActivities().get(activtyIndex).getName());
            dialogBinding.breaksTextView.setText(hobby.getHobbyActivities().get(activtyIndex).getBreakLength().toString());
            dialogBinding.breakAfterText.setText(hobby.getHobbyActivities().get(activtyIndex).getBreakAfterActivity().toString());
            dialogBinding.repetitionTextView.setText(String.valueOf(hobby.getHobbyActivities().get(activtyIndex).getRepetitions()));
            dialogBinding.activityTimeTextView.setText(hobby.getHobbyActivities().get(activtyIndex).getTimeNeeded().toString());
        }

        builder.setPositiveButton("Save", (dialog, which) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            String toastMessage = "";
            try {
                HobbyActivity activity = new HobbyActivity(
                        dialogBinding.activityNameTextView.getText().toString(),
                        new Time(sdf.parse(dialogBinding.activityTimeTextView.getText().toString()).getTime()),
                        Integer.parseInt(dialogBinding.repetitionTextView.getText().toString()),
                        new Time(sdf.parse(dialogBinding.breaksTextView.getText().toString()).getTime()),
                        new Time(sdf.parse(dialogBinding.breakAfterText.getText().toString()).getTime()));

                if(!MyTimeTextWatcher.isValidTime(dialogBinding.activityTimeTextView.getText().toString())){
                    toastMessage += "Invalid activity time";
                } else if(!MyTimeTextWatcher.isValidTime(dialogBinding.breakAfterText.getText().toString())){
                    toastMessage += "Invalid break time";
                } else if(!MyTimeTextWatcher.isValidTime(dialogBinding.breaksTextView.getText().toString())){
                    toastMessage += "Invalid 'break after' time";
                } else if(activtyIndex == -1) {
                    hobby.getHobbyActivities().add(activity);
                    toastMessage += "Activity added successfully";
                } else {
                    hobby.getHobbyActivities().remove(activtyIndex);
                    hobby.getHobbyActivities().add(activtyIndex,activity);
                    toastMessage += "Activity updated successfully";
                }
                Log.i("Anandu","activity saved as "+activity);
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

    @Override
    public void listItemClickCallback(View v, int position) {
        initActivityDialogBox(hobby, position);
    }

}