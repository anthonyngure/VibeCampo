/*
 * Copyright (c) 2016. VibeCampo Social Network
 *
 * Website : http://www.vibecampo.com
 */

package com.vibecampo.android.utils;

import android.os.Environment;

import com.vibecampo.android.storage.SimpleStorage;
import com.vibecampo.android.storage.Storage;

import java.io.File;

import ke.co.toshngure.basecode.log.BeeLog;


/**
 * Created by Anthony Ngure on 7/1/2016.
 * Email : anthonyngure25@gmail.com.
 */
public class StorageUtils {

    private static final String TAG = StorageUtils.class.getSimpleName();
    public static final String APP_FOLDER = "VibeCampo";
    public static final String PROFILE_PHOTOS = "VibeCampo"+File.separator+"Profiles";
    private static final String PENDING_UPLOAD = "VibeCampo"+File.separator+"Uploads";
    private static final String CAMERA_PHOTO_FOLDER = "VibeCampo";
    private static final String DEBUG_LOGS = "VibeCampo"+File.separator+"Logs";
    private static final String CHAT_BACKGROUNDS = "VibeCampo"+File.separator+"Wallpapers";
    public static final String CACHED = "cached";
    private static StorageUtils mInstance;
    private String mFolderName;
    private String fileName;
    private String mPath;
    private boolean asImage = false;

    private StorageUtils(String folderName) {
        this.mFolderName = folderName;
    }

    public static String getPendingUploadDirectory() {
        Storage storage = SimpleStorage.getExternalStorage();
        storage.createDirectory(PENDING_UPLOAD, false);
        return PENDING_UPLOAD;
    }

    public static String getProfilePhotoDirectory() {
        Storage storage = SimpleStorage.getExternalStorage();
        storage.createDirectory(PROFILE_PHOTOS, false);
        return PROFILE_PHOTOS;
    }

    public static String getDebugLogsDirectory() {
        Storage storage = SimpleStorage.getExternalStorage();
        storage.createDirectory(DEBUG_LOGS, false);
        return DEBUG_LOGS;
    }

    public static String getChatBackgroundsDirectory() {
        Storage storage = SimpleStorage.getExternalStorage();
        storage.createDirectory(CHAT_BACKGROUNDS, false);
        return CHAT_BACKGROUNDS;
    }

    public static StorageUtils getInstance(String folderName) {
        mInstance = new StorageUtils(folderName);
        return mInstance;
    }

    public static File getFolder(String folderName) {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), APP_FOLDER + "/" + folderName);
        if (!file.exists()) {
            if (file.mkdirs()) {
                BeeLog.d(TAG, file.getAbsolutePath() + " Created");
            }
        }
        return file;
    }

    public static String createNewCameraImage() {
        return StorageUtils.getInstance(StorageUtils.CAMERA_PHOTO_FOLDER)
                .name(String.valueOf(System.currentTimeMillis()))
                .asImage()
                .build()
                .getPath();
    }

    public static void delete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                BeeLog.e(TAG, e);
            }
        }
    }

    public StorageUtils asImage() {
        mInstance.setAsImage(true);
        return mInstance;
    }

    public StorageUtils name(String fileName) {
        mInstance.setFileName(fileName);
        return mInstance;
    }

    public StorageUtils build() {
        if (fileName == null) {
            fileName = String.valueOf(System.currentTimeMillis());
        }
        if (asImage) {
            fileName = fileName + ".jpg";
        }
        mPath = getFolder(mFolderName).getAbsolutePath() + "/" + fileName;
        return mInstance;
    }

    public String getPath() {
        return mPath;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private void setAsImage(boolean asImage) {
        this.asImage = asImage;
    }

}
