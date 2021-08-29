package com.example.habbittrainer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbittrainer.databinding.HobbyListViewBinding;
import com.example.habbittrainer.models.Hobby;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HobbyListAdaptor extends RecyclerView.Adapter<HobbyListAdaptor.MyViewHolder> {

    private List<Hobby> hobbies = new ArrayList<>();
    private ListItemCallbackContract callbackContract;

    public HobbyListAdaptor(List<Hobby> hobbies, ListItemCallbackContract callbackContract) {
        for (Hobby h: hobbies) {
            Log.i("ANANDU",h.toString());
        }
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
        HobbyListViewBinding hobbyListViewBinding = HobbyListViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        MyViewHolder viewHolder = new MyViewHolder(hobbyListViewBinding);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Log.i("Anandu","setting text to "+ hobbies.get(position).getName());
        holder.hobbyListViewBinding.hobbyName.setText(hobbies.get(position).getName());
        holder.hobbyListViewBinding.getRoot().setTag(position);
        holder.hobbyListViewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Anandu","Clicked "+v.getTag());
                callbackContract.listItemClickCallback(v, (Integer)v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("Anandu","Item count "+ hobbies.size());
        return hobbies.size();
    }
}
