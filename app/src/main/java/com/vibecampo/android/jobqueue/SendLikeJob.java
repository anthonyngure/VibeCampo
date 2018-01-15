package com.vibecampo.android.jobqueue;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.loopj.android.http.RequestParams;
import com.vibecampo.android.App;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;
import com.vibecampo.android.settings.PrefUtils;

import org.json.JSONObject;

import ke.co.toshngure.basecode.log.BeeLog;
import ke.co.toshngure.basecode.networking.ConnectionListener;
import ke.co.toshngure.basecode.networking.ResponseHandler;

/**
 * Created by Anthony Ngure on 07/01/2017.
 * Email : anthonyngure25@gmail.com.
 * Company : VibeCampo Social Network..
 */

public class SendLikeJob extends Job implements ConnectionListener {

    private static final String TAG = "SendLikeJob";

    private long feedId;

    public SendLikeJob(long feedId) {
        super(new Params(Priority.MID)
                .setSingleId(String.valueOf(feedId))
                .requireNetwork()
                .persist()
                .groupBy(TAG)
        );
        this.feedId = feedId;
    }

    @Override
    public void onAdded() {
        BeeLog.d(TAG, "onAdded");
    }

    @Override
    public void onRun() throws Throwable {
        BeeLog.d(TAG, "onRun");
        connect();
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        BeeLog.d(TAG, "onCancel");
        /*Feed feed = Database.getInstance().getDaoSession().getFeedDao().load(feedId);
        if (feed != null) {
            if (isLike) {
                feed.setLiked(false);
            } else {
                feed.setLiked(true);
            }
            feed.update();
        }*/
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        BeeLog.d(TAG, "shouldReRunOnThrowable");
        BeeLog.d(TAG, String.valueOf(throwable.getMessage()));
        /*Feed feed = Database.getInstance().getFeedDao().load(feedId);
        if (feed != null) {
            return RetryConstraint.RETRY;
        } else {
            return RetryConstraint.CANCEL;
        }*/

        return RetryConstraint.RETRY;
    }

    @Override
    public void connect() {
        String url = Client.absoluteUrl(BackEnd.EndPoints.POST);
        RequestParams params = new RequestParams();
        params.put(BackEnd.Params.VIBE_ID, feedId);
        params.put(BackEnd.Params.QUERY, BackEnd.QUERY.POST_VIBE_LIKE_UNLIKE);
        params.put(BackEnd.Params.PUBLISHER_ID, PrefUtils.getInstance().getUser().getUserId());
        Client.getInstance().getSyncHttpClient().post(url, params, new ResponseHandler(this));
    }

    @Override
    public void onConnectionStarted() {

    }

    @Override
    public void onConnectionFailed(int statusCode, JSONObject response) {
        BeeLog.d(TAG, String.valueOf(response.toString()));
    }

    @Override
    public void onConnectionSuccess(JSONObject response) {
        BeeLog.d(TAG, String.valueOf(response.toString()));
    }

    @Override
    public void onConnectionProgress(int progress) {
        BeeLog.i(TAG, String.valueOf(progress));
    }

    @Override
    public Context getListenerContext() {
        return App.getInstance();
    }
}
