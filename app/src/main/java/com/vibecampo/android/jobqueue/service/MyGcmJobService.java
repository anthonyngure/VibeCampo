package com.vibecampo.android.jobqueue.service;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;
import com.vibecampo.android.App;

/**
 * Created by Anthony Ngure on 27/11/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class MyGcmJobService extends GcmJobSchedulerService {
    @NonNull
    @Override
    protected JobManager getJobManager() {
        // return your JobManager instance
        return App.getInstance().getJobManager();
    }
}
