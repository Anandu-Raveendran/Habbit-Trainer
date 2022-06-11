package com.example.habbittrainer.interfaces;

import android.view.View;

public interface ListItemCallbackContract {
    public void listItemClickCallback(View v, int position);

    void deleteItem(View v, Integer tag);

    void playItem(View v, Integer tag);
}
