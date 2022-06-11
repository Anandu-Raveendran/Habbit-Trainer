package com.example.habbittrainer.Adaptors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbittrainer.interfaces.ListItemCallbackContract;
import com.example.habbittrainer.R;
import com.example.habbittrainer.databinding.HobbyListViewBinding;
import com.example.habbittrainer.models.Hobby;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HobbyListAdaptor extends RecyclerView.Adapter<HobbyListAdaptor.MyViewHolder> {

    private List<Hobby> hobbies = new ArrayList<>();
    private ListItemCallbackContract callbackContract;
    Context context;

    public HobbyListAdaptor(Context context, List<Hobby> hobbies, ListItemCallbackContract callbackContract) {
        for (Hobby h : hobbies) {
            Log.i("ANANDU", h.toString());
        }
        this.context = context;
        this.hobbies = hobbies;
        this.callbackContract = callbackContract;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        HobbyListViewBinding hobbyListViewBinding;

        public MyViewHolder(@NotNull HobbyListViewBinding hobbyListViewBinding) {
            super(hobbyListViewBinding.getRoot());
            this.hobbyListViewBinding = hobbyListViewBinding;
        }
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        HobbyListViewBinding hobbyListViewBinding = HobbyListViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        MyViewHolder viewHolder = new MyViewHolder(hobbyListViewBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Log.i("Anandu", "setting text to " + hobbies.get(position).getName());
        holder.hobbyListViewBinding.hobbyNameText.setText(hobbies.get(position).getName());
        holder.hobbyListViewBinding.timeText.setText(hobbies.get(position).getScheduledTime().toString());
        holder.hobbyListViewBinding.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.capture));

        int streak = hobbies.get(position).getScoreStreak();
        if (streak > 0) {
            holder.hobbyListViewBinding.streakFire.setVisibility(View.VISIBLE);
            holder.hobbyListViewBinding.streakCount.setVisibility(View.VISIBLE);
            holder.hobbyListViewBinding.streakCount.setText(String.valueOf(streak));
        }
        holder.hobbyListViewBinding.getRoot().setTag(position);
        holder.hobbyListViewBinding.playButton.setTag(position);
        holder.hobbyListViewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Anandu", "Clicked " + v.getTag());
                callbackContract.listItemClickCallback(v, (Integer) v.getTag());
            }
        });
/*        holder.hobbyListViewBinding.deleteBtn.setTag(position);
        holder.hobbyListViewBinding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Anandu", "Delete Clicked " + v.getTag());
                callbackContract.deleteItem(v, (Integer) v.getTag());
            }
        });
  */
        holder.hobbyListViewBinding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Anandu", "Play Clicked " + v.getTag());
                callbackContract.playItem(v, (Integer) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("Anandu", "Item count " + hobbies.size());
        return hobbies.size();
    }
}
