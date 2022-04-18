package com.teys.aenaemma;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.*;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by cgo on 22/06/16.
 */
public class JSONWriter {
    private static String appFilePath ="AenaStorage";

    public void writeNewFile(Activity act, Questionnaire cue, String fileName){


        Gson gson = new Gson();
        //Object to JSON in file
        String s = gson.toJson(cue);
        File file;

        file = new File (act.getExternalFilesDir(appFilePath), fileName);
        if(file.getParentFile().mkdirs()) {
            String debug = "Esto existe";
        }

        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public File getStorageDir(String fileName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(null), fileName);
        if (!file.mkdirs()) {
            Log.e("LOG_TAG", "Directory not created");
        }
        return file;
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }



}
