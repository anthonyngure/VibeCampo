package com.vibecampo.android.utils;

import com.vibecampo.android.storage.SimpleStorage;

import java.io.File;
import java.text.DateFormat;

/**
 * Created by Anthony Ngure on 27/02/2017.
 * Email : anthonyngure25@gmail.com.
 * Company : VibeCampo Social Network..
 */

public class FileLog {

    public static final String TAG = "ToshNgure";
    private static File logFile;

    public static void toFile(String tag, String message){

        if (logFile == null){
            String fileName = System.currentTimeMillis()+".txt";
            SimpleStorage.getExternalStorage().createFile(StorageUtils.getDebugLogsDirectory(), fileName,
                    "-------"+ DateFormat.getDateTimeInstance().format(System.currentTimeMillis())+"--------\n");
            logFile = SimpleStorage.getExternalStorage().getFile(StorageUtils.getDebugLogsDirectory(), fileName);
        }
        SimpleStorage.getExternalStorage().appendFile(StorageUtils.getDebugLogsDirectory(), logFile.getName(),
                tag+" : "+DateFormat.getDateTimeInstance().format(System.currentTimeMillis())+"\n"+message+"\n");
    }
}
