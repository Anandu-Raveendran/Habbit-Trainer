package com.example.habbittrainer.Adaptors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbittrainer.R;
import com.example.habbittrainer.databinding.ActivityListViewBinding;
import com.example.habbittrainer.interfaces.ListItemCallbackContract;
import com.example.habbittrainer.models.HobbyActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActivityListAdaptor extends RecyclerView.Adapter<ActivityListAdaptor.MyViewHolder> {

    private List<HobbyActivity> activities = new ArrayList<>();
    private ListItemCallbackContract contract;
    private int[] colors;
    private Context context;

    public ActivityListAdaptor(Context context, List<HobbyActivity> activities, ListItemCallbackContract callbackContract) {
        for (HobbyActivity h : activities) {
            Log.i("ANANDU", h.toString());

        }
        this.activities = activities;
        this.contract = callbackContract;
        this.context = context;
        colors = context.getResources().getIntArray(R.array.listcolors);
    }

    public void setActivities(List<HobbyActivity> activities) {
        this.activities = activities;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ActivityListViewBinding binding;

        public MyViewHolder(@NotNull ActivityListViewBinding activityListViewBinding) {
            super(activityListViewBinding.getRoot());
            binding = activityListViewBinding;
        }
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ActivityListViewBinding activityListViewBinding = ActivityListViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        MyViewHolder viewHolder = new MyViewHolder(activityListViewBinding);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Log.i("Anandu", "setting text to " + activities.get(position).getName());
        holder.binding.activityNameText.setText(activities.get(position).getName());
        holder.binding.itemTimeText.setText(activities.get(position).getTimeNeeded().toString());
        holder.binding.getRoot().setTag(position);
        holder.binding.deleteItemBtn.setTag(position);
        ViewGroup.LayoutParams params = holder.binding.durationView.getLayoutParams();

        // Change list item height
        int mins = 0, hours = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mins = activities.get(position).getTimeNeeded().getMinute();
            hours = activities.get(position).getTimeNeeded().getHour();
        }
        if (mins > 0)
            params.height = params.height + mins / 2;
        params.height = params.height + hours * 60;
        if (params.height > 300)
            params.height = 300;

        holder.binding.durationView.setLayoutParams(params);

        //Change list color at random
        Random rand = new Random();
        int n = rand.nextInt(5);
        holder.binding.durationView.setBackgroundColor(colors[n]);
        holder.binding.activityNameText.setTextColor(colors[n]);
        holder.binding.durationView.setLayoutParams(params);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contract.listItemClickCallback(v, (Integer) v.getTag());
            }
        });
        holder.binding.deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contract.deleteItem(v, (Integer) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("Anandu", "Item count " + activities.size());
        return activities.size();
    }
}
