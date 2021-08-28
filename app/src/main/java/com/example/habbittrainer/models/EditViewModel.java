package com.example.habbittrainer.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditViewModel extends ViewModel {
    private MutableLiveData<Hobby> hobby = new MutableLiveData<>();

    public LiveData<Hobby> getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby.setValue(hobby);
    }
}