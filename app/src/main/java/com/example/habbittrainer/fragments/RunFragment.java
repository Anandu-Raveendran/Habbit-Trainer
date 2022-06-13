package com.example.habbittrainer.fragments;

import static java.lang.System.exit;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.habbittrainer.MyTimeTextWatcher;
import com.example.habbittrainer.R;
import com.example.habbittrainer.databinding.FragmentRunBinding;
import com.example.habbittrainer.models.Hobby;
import com.example.habbittrainer.models.HobbyActivity;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RunFragment extends Fragment {

    private Hobby hobby;
    private FragmentRunBinding binding;
    private List<HobbyActivity> activities;
    private CountDownTimer countDownTimer;
    private long timeInMillis;
    private boolean timerRunning = false;
    private int currentActivity_index = -1;
    private int currentRepetition = 0;
    private long startTime;

    private enum CurrentState {inActivity, inBreak, inEndBreak}

    ;
    private CurrentState myState;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRunBinding.inflate(inflater);

        if (getArguments() != null) {
            hobby = (Hobby) getArguments().getSerializable("hobby");
            activities = hobby.getHobbyActivities();
        } else {
            exit(1);
        }

        binding.playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    pauseTimer();
                    binding.playPauseBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                } else {
                    startTimer();
                    binding.playPauseBtn.setImageResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });
        binding.exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myExit();
            }
        });
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();
            }
        });

        nextActivity();
        return binding.getRoot();
    }

    private void updateUI() {
        Log.i("Anandu", "updateUI current index " + currentActivity_index + " size " + activities.size());
        //Current activity card
        if (currentActivity_index < activities.size()) {
            binding.repetitions.setText(currentRepetition + " reps left");
            binding.runningActivityName.setText(activities.get(currentActivity_index).getName());
            binding.timerText.setText(activities.get(currentActivity_index).getTimeNeeded().toString());
            binding.progressBar.setProgress(currentActivity_index);
        }

        if (currentActivity_index == 0) {
            //Previous activity card
            binding.previousActivityNameText.setVisibility(View.INVISIBLE);
            binding.previousActivityNameText.setEnabled(false);
            binding.previousActivityTimeText.setVisibility(View.INVISIBLE);
            binding.previousActivityTimeText.setEnabled(false);
            binding.topCard.setVisibility(View.INVISIBLE);
            binding.topCard.setEnabled(false);
        } else if (currentActivity_index == 1) {
            binding.previousActivityNameText.setVisibility(View.VISIBLE);
            binding.previousActivityNameText.setEnabled(true);
            binding.previousActivityTimeText.setVisibility(View.VISIBLE);
            binding.previousActivityTimeText.setEnabled(true);
            binding.topCard.setVisibility(View.VISIBLE);
            binding.topCard.setEnabled(true);
            binding.previousActivityTimeText.setText(activities.get(currentActivity_index - 1).getName());
            binding.previousActivityNameText.setText(activities.get(currentActivity_index - 1).getTimeNeeded().toString());
        } else {
            binding.previousActivityTimeText.setText(activities.get(currentActivity_index - 1).getName());
            binding.previousActivityNameText.setText(activities.get(currentActivity_index - 1).getTimeNeeded().toString());
        }

        //Bottom activity card
        if (currentActivity_index + 1 >= activities.size()) {
            binding.nextActivityNameText.setVisibility(View.INVISIBLE);
            binding.nextActivityNameText.setEnabled(false);
            binding.nextActivityTimeText.setVisibility(View.INVISIBLE);
            binding.nextActivityTimeText.setEnabled(false);
            binding.bottomCard.setEnabled(false);
            binding.bottomCard.setVisibility(View.INVISIBLE);
        } else {
            binding.nextActivityNameText.setText(activities.get(currentActivity_index + 1).getName());
            binding.nextActivityTimeText.setText(activities.get(currentActivity_index + 1).getTimeNeeded().toString());
        }
        //Next activity card

    }

    private void startTimer() {
        if (timerRunning)
            pauseTimer();

        Log.i("Anandu", "Timer started " + (new Time(timeInMillis)).toString() + " millis " + timeInMillis);
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timerFinished();
            }
        }.start();
        timerRunning = true;
    }

    private void pauseTimer() {
        Log.i("Anandu", "Timer paused " + (new Time(timeInMillis)).toString());
        countDownTimer.cancel();
        timerRunning = false;
    }

    private void nextActivity() {
        Log.i("Anandu", "Next timer " + (new Time(timeInMillis)).toString());
        if (currentActivity_index < activities.size() - 1) {
            currentActivity_index++;
            myState = CurrentState.inActivity;
            LocalTime time = activities.get(currentActivity_index).getTimeNeeded();
            startTime = timeInMillis = MyTimeTextWatcher.timeToMillis(time);
            currentRepetition = activities.get(currentActivity_index).getRepetitions();
            currentRepetition--;
            Log.i("Anandu", "time from hobby hours" + time.getHour() + " mins " + time.getMinute() + "seconds" + time.getSecond() + " mills " + timeInMillis);
            updateUI();
            if (currentActivity_index != 0) // dont start first activity by self
                startTimer();
        } else {
            binding.nextBtn.setVisibility(View.INVISIBLE);
            binding.nextBtn.setEnabled(false);
        }

    }

    private void myExit() {
        Log.i("Anandu", "My Exit");
        NavHostFragment.findNavController(RunFragment.this).navigateUp();
    }

    private void updateTimerText() {
        long hours = (timeInMillis / 1000) / 3600;
        long mins = ((timeInMillis / 1000) % 3600) / 60;
        long seconds = (timeInMillis / 1000) % 60;

        String timeLeft;
        if (hours > 0) {
            timeLeft = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, mins, seconds);
        } else {
            timeLeft = String.format(Locale.getDefault(), "%02d:%02d", mins, seconds);
        }
        Log.i("Anandu", "Update Timer millis " + timeInMillis + " timeleft " + timeLeft + " progress " + startTime + " " + timeInMillis + " " +
                ((startTime - timeInMillis + 1001) * 100) / startTime);

        binding.timerText.setText(timeLeft);
        binding.progressBar.setProgress((int) (((startTime - timeInMillis + 1001) * 100) / startTime));

    }

    private void nextFlow() {
        LocalTime time;
        Log.i("Anandu", "curent rep " + currentRepetition + " state " + myState);
        //If more reps are there and it is in activity state
        if (currentRepetition > 0 && myState == CurrentState.inActivity) {
            time = activities.get(currentActivity_index).getBreakLength();
            currentRepetition--;
            binding.repetitions.setText(String.valueOf("" + currentRepetition + " reps left"));
            binding.runningActivityName.setText("Break for " + activities.get(currentActivity_index).getName());
            myState = CurrentState.inBreak;
        } else if (currentRepetition >= 0 && myState == CurrentState.inBreak) {
            time = activities.get(currentActivity_index).getTimeNeeded();
            binding.runningActivityName.setText(activities.get(currentActivity_index).getName());
            myState = CurrentState.inActivity;
        } else if (myState == CurrentState.inEndBreak) {
            if (currentActivity_index < activities.size()) {
                nextActivity();
                time = activities.get(currentActivity_index).getBreakAfterActivity();
            } else {
                return;
            }
        } else {
            time = activities.get(currentActivity_index).getBreakAfterActivity();
            binding.runningActivityName.setText("Final break: " + activities.get(currentActivity_index).getName());
            myState = CurrentState.inEndBreak;
        }
        startTime = timeInMillis = MyTimeTextWatcher.timeToMillis(time);
        Log.i("Anandu", "state " + myState + " time from hobby hours" + time.getHour() + " mins " + time.getMinute() + "seconds" + time.getSecond() + " mills " + timeInMillis);
        //updateUI();

        startTimer();
    }

    private void timerFinished() {
        timerRunning = false;
        nextFlow();
        Log.i("Anandu", "Timer Finish ");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerRunning = false;
    }
}