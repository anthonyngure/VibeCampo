/*
 * Copyright (c) 2016. VibeCampo Social Network
 *
 * Website : http://www.vibecampo.com
 */

package com.vibecampo.android.network;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.SyncHttpClient;
import com.vibecampo.android.BuildConfig;
import com.vibecampo.android.model.User;
import com.vibecampo.android.settings.PrefUtils;

import ke.co.toshngure.basecode.log.BeeLog;


/**
 * Created by Anthony Ngure on 4/17/2016.
 * Email : anthonyngure25@gmail.com.
 * Company : VibeCampo Social Network..
 */
public class Client {

    private static final String TAG = Client.class.getSimpleName();
    public static Client mInstance;
    private AsyncHttpClient mClient;
    private SyncHttpClient syncHttpClient;

    private Client() {
        mClient = getClient();
    }

    @Deprecated
    public static synchronized Client getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Client();
        }
        return mInstance;
    }

    public static synchronized Client getInstance() {
        if (mInstance == null) {
            mInstance = new Client();
        }
        return mInstance;
    }

    public static String absoluteUrl(String relativeUrl) {
        User user = PrefUtils.getInstance().getUser();
        if (user != null) {
            String url;
            url = BackEnd.BASE_URL + relativeUrl + "/" + user.getAppkey();
            BeeLog.d(TAG, "absoluteUrl " + url);
            return url;
        } else {
            String url = BackEnd.BASE_URL + relativeUrl;
            BeeLog.d(TAG, "absoluteUrl " + url);
            return url;
        }
    }

    public static String appendAppkey(String pageUrl) {
        User user = PrefUtils.getInstance().getUser();
        if (user != null) {
            String url;
            url = pageUrl + BackEnd.Params.APP_KEY + user.getAppkey();
            BeeLog.d(TAG, "appendAppkey to page url, " + url);
            return url;
        } else {
            return pageUrl;
        }
    }

    public AsyncHttpClient getClient() {
        if (mClient == null) {
            /**
             * Client setup
             */
            mClient = new AsyncHttpClient(true, 80, 443);
            setUpClient(mClient);
        }
        return mClient;
    }

    public SyncHttpClient getSyncHttpClient() {
        if (syncHttpClient == null) {
            syncHttpClient = new SyncHttpClient();
            setUpClient(syncHttpClient);
        }
        return syncHttpClient;
    }

    private void setUpClient(AsyncHttpClient client) {
        client.setUserAgent(BuildConfig.APPLICATION_ID);
        client.setEnableRedirects(false, true);
        client.setLoggingEnabled(BuildConfig.DEBUG);

        client.addHeader("Accept-Encoding", "gzip");
        client.addHeader("Accept", "application/json");
        client.setBasicAuth(String.valueOf(BuildConfig.VERSION_NAME),
                String.valueOf(BuildConfig.VERSION_CODE));

        /*client.setTimeout(30000);
        client.setResponseTimeout(60000);*/

        client.setTimeout(10000);
        client.setResponseTimeout(20000);

    }


    public void invalidate() {
        mInstance = null;
        mClient = null;
    }


}
