package com.example.habbittrainer;

import android.text.Editable;
import android.text.TextWatcher;

public class MyTimeTextWatcher implements TextWatcher {
    boolean _ignore = false;
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(_ignore) {
            return;
        }
        if(s.length() > 0) {
            _ignore = true;
            //Check if first letter is less than digit 6
            if (Character.isDigit(s.charAt(0))) {
                if (Integer.parseInt(s.subSequence(0, 1).toString()) > 6) {
                    s.replace(0, 0, "6");
                }
            } else s.replace(0, 0, "0");

            // Check if 2nd is digit or : if : put 0 in front
            if(s.length() > 1) {
                if (Character.isDigit(s.charAt(1))) {
                } else if(s.charAt(1) == ':'){
                    s.insert(0,"0");
                } else s.replace(1, 1, "0");
            }
            //Check if 3rd digit it a : else put it
            if(s.length() > 2) {
                if (s.charAt(2) == ':') {
                } else s.replace(2, 2, ":");
            }
            //check if 4rth is a digit less than 6
            if(s.length() > 3) {
                if (Character.isDigit(s.charAt(3))) {
                    if (Integer.parseInt(s.subSequence(0, 1).toString()) > 6) {
                        s.replace(3, 3, "6");
                    }
                } else s.replace(3, 3, "0");
            }
            //check if 5th is a digit
            if(s.length() > 4) {
                if (Character.isDigit(s.charAt(4))) {
                } else s.replace(4, 4, "0");
            }
            _ignore = false;
        }
    }
}
