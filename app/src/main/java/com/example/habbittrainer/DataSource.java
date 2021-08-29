package com.example.habbittrainer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.habbittrainer.models.Hobby;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DataSource {

    final static String fileName = "HobbyTracker.txt";

    public static void write(Context context, List<Hobby> hobbies) {
        // add-write text into file
        try {
            Log.i("Anandu","Trying to write data "+hobbies);
            Integer i = 0;
            for (Hobby h: hobbies) {
                Log.i(i.toString(), h.toString());
                i++;
            }
            FileOutputStream fileOut = context.openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream outputWriter=new ObjectOutputStream(fileOut);
            outputWriter.writeObject(hobbies);
            outputWriter.close();

            //display file saved message
            Toast.makeText(context, "Data saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Hobby> readHobbies(Context context){
        //reading text from file
        List<Hobby> hobbies = null;
        try {
            Log.i("Anandu","Trying to read data");
            FileInputStream fileIn=context.openFileInput(fileName);
            ObjectInputStream inputStream = new ObjectInputStream(fileIn);

            hobbies = (List<Hobby>) inputStream.readObject();
            Log.i("Anandu","Read data: "+hobbies);
            Integer i = 0;
            if(hobbies != null)
            for (Hobby h: hobbies) {
                Log.i(i.toString(), h.toString());
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hobbies;
    }
}
