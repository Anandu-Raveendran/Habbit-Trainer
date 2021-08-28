package com.example.habbittrainer;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbittrainer.databinding.ActivityListViewBinding;
import com.example.habbittrainer.models.Hobby;
import com.example.habbittrainer.models.HobbyActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ActivityListAdaptor extends RecyclerView.Adapter<ActivityListAdaptor.MyViewHolder> {

    private List<HobbyActivity> activities = new ArrayList<>();

    public ActivityListAdaptor(List<HobbyActivity> activities) {
        this.activities = activities;
    }

    public void setActivities(List<HobbyActivity> activities) {
        this.activities = activities;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ActivityListViewBinding binding;
        public MyViewHolder(ActivityListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ActivityListViewBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.binding.activityName.setText(activities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }
}
