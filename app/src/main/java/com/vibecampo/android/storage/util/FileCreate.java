package com.vibecampo.android.storage.util;

import java.io.File;

/**
 * Created by Anthony Ngure on 27/02/2017.
 * Email : anthonyngure25@gmail.com.
 * Company : VibeCampo Social Network..
 */

public class FileCreate {

    public boolean isCreated;
    public File file;

    public FileCreate(boolean created, File file) {
        this.isCreated = created;
        this.file = file;
    }
}
