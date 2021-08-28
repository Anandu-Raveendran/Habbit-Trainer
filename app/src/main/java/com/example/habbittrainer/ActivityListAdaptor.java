package com.example.habbittrainer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbittrainer.databinding.ActivityListViewBinding;
import com.example.habbittrainer.models.Hobby;
import com.example.habbittrainer.models.HobbyActivity;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ActivityListAdaptor extends RecyclerView.Adapter<ActivityListAdaptor.MyViewHolder> {

    private List<HobbyActivity> activities = new ArrayList<>();

    public ActivityListAdaptor(List<HobbyActivity> activities) {
        for (HobbyActivity h: activities) {
            Log.i("ANANDU",h.toString());

        }
        this.activities = activities;
    }

    public void setActivities(List<HobbyActivity> activities) {
        this.activities = activities;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public MyViewHolder(@NotNull ActivityListViewBinding activityListViewBinding) {
            super(activityListViewBinding.getRoot());
            textView = activityListViewBinding.hobbyName;
        }
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ActivityListViewBinding activityListViewBinding = ActivityListViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        MyViewHolder viewHolder = new MyViewHolder(activityListViewBinding);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Log.i("Anandu","setting text to "+activities.get(position).getName());
        holder.textView.setText(activities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        Log.i("Anandu","Item count "+activities.size());
        return activities.size();
    }
}
