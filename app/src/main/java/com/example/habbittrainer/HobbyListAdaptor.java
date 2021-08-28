package com.example.habbittrainer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbittrainer.databinding.ActivityListViewBinding;
import com.example.habbittrainer.databinding.HobbyListViewBinding;
import com.example.habbittrainer.models.Hobby;
import com.example.habbittrainer.models.HobbyActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HobbyListAdaptor extends RecyclerView.Adapter<HobbyListAdaptor.MyViewHolder> {

    private List<Hobby> hobbies = new ArrayList<>();

    public HobbyListAdaptor(List<Hobby> hobbies) {
        for (Hobby h: hobbies) {
            Log.i("ANANDU",h.toString());

        }
        this.hobbies = hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public MyViewHolder(@NotNull HobbyListViewBinding hobbyListViewBinding) {
            super(hobbyListViewBinding.getRoot());
            textView = hobbyListViewBinding.hobbyName;
        }
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        HobbyListViewBinding hobbyListViewBinding = HobbyListViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        MyViewHolder viewHolder = new MyViewHolder(hobbyListViewBinding);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Log.i("Anandu","setting text to "+ hobbies.get(position).getName());
        holder.textView.setText(hobbies.get(position).getName());
    }

    @Override
    public int getItemCount() {
        Log.i("Anandu","Item count "+ hobbies.size());
        return hobbies.size();
    }
}
