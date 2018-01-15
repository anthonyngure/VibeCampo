/*
 * Copyright (c) 2017. Laysan Incorporation
 * Website http://laysan.co.ke
 * Tel +254723203475/+254706356815
 */

package com.vibecampo.android.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.vibecampo.android.R;
import com.vibecampo.android.model.User;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;
import com.vibecampo.android.network.Meta;
import com.vibecampo.android.settings.PrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ke.co.toshngure.basecode.app.BaseAppActivity;
import ke.co.toshngure.basecode.app.DialogAsyncTask;
import ke.co.toshngure.basecode.log.BeeLog;
import ke.co.toshngure.basecode.networking.ConnectionListener;
import ke.co.toshngure.basecode.utils.BaseUtils;


/**
 * Created by Anthony Ngure on 7/1/2016.
 * Email : anthonyngure25@gmail.com.
 */

@SuppressLint("Registered")
public class BaseActivity extends BaseAppActivity implements ConnectionListener {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Client.getInstance().getClient().cancelRequests(this, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isLoggedIn() {
        //return ((getUser() != null) && ((AccessToken.getCurrentAccessToken() != null)));
        return (getUser() != null);
    }

    protected User getUser(){
        return PrefUtils.getInstance().getUser();
    }

    public void onAuthSuccessful(JSONObject data, Meta meta){

        User user = BaseUtils.getSafeGson().fromJson(data.toString(), User.class);
        PrefUtils.getInstance().saveUser(user);

        Client.getInstance().invalidate();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        toast(meta.message);

        this.finish();
    }

    public void signOut() {

        new DialogAsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                //mLocalUserConnectedRef.setValue(ServerValue.TIMESTAMP);
                /*try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                User localUser = PrefUtils.getInstance().getUser();
                PrefUtils.getInstance().signOut();
                //App.getInstance().getJobManager().clear();
                //Database.getInstance().clean();
                Client.getInstance().invalidate();
                PrefUtils.getInstance().writeString(R.string.pref_email, localUser.getEmail());
                //PrefUtils.getInstance().writeString(R.string.pref_phone, localUser.getPhone());
                return null;
            }

            @Override
            protected Activity getActivity() {
                return getThis();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent(getActivity(), IntroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        }.execute();
    }

    public void openWebsite(String url) {

        //WebViewActivity.start(this, url, getString(R.string.app_name));

        /*new FinestWebView.Builder(this).theme(R.style.FinestWebViewTheme)
                .titleDefaultRes(R.string.app_name)
                .showUrl(true)
                .statusBarColorRes(R.color.colorPrimaryDark)
                .toolbarColorRes(R.color.colorPrimary)
                .titleColorRes(R.color.finestWhite)
                .updateTitleFromHtml(true)
                .urlColorRes(android.R.color.darker_gray)
                .iconDefaultColorRes(R.color.finestWhite)
                .progressBarColorRes(R.color.colorAccent)
                .progressBarHeight(BaseUtils.dpToPx(2))
                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                .showSwipeRefreshLayout(true)
                .swipeRefreshColorRes(R.color.colorPrimaryDark)
                .menuSelector(R.drawable.selector_light_theme)
                .menuTextGravity(Gravity.CENTER)
                .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                .dividerHeight(0)
                .gradientDivider(false)
                .webViewAllowFileAccess(true)
                .webViewAllowContentAccess(true)
                .webViewAllowFileAccessFromFileURLs(true)
                .webViewAllowUniversalAccessFromFileURLs(true)
                .webViewUserAgentString(BuildConfig.APPLICATION_ID)
                .webViewAppCacheEnabled(true)
                .webViewJavaScriptEnabled(true)
                .webViewJavaScriptCanOpenWindowsAutomatically(true)
                .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
                .show(url);*/
    }

    @Override
    public void connect() {

    }

    @Override
    public void onConnectionStarted() {
        showProgressDialog();
    }


    @Override
    public void onConnectionFailed(int statusCode, JSONObject response) {
        BeeLog.d(TAG, "Connection failed! " + statusCode + ", " + String.valueOf(response));
        hideProgressDialog();
        if ((statusCode == 0) || (statusCode == 408)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.connection_timed_out)
                    .setMessage(R.string.error_connection)
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            connect();
                        }
                    }).create().show();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true)
                    .setTitle(R.string.server_error)
                    .setMessage(response.toString())
                    .setNegativeButton(R.string.report, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton(android.R.string.ok, null);
            builder.create().show();
        }

    }

    protected void showErrorAlertDialog(String message) {
        new AlertDialog.Builder(this).setCancelable(true)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show();
    }

    protected void onSuccessResponse(JSONObject data, Meta meta) {

    }

    protected void onSuccessResponse(JSONArray data, Meta meta) {

    }

    protected void onErrorResponse(Meta meta){
        showErrorAlertDialog(meta.message);
    }

    @Override
    public void onConnectionSuccess(JSONObject response) {
        BeeLog.d(TAG, "onConnectionSuccess, Response = " + String.valueOf(response));
        hideProgressDialog();
        try {
            Meta meta;
            meta = BaseUtils.getSafeGson().fromJson(response.getJSONObject(BackEnd.Response.META).toString(), Meta.class);
            if (meta.status == BackEnd.Response.SUCCESS_CODE){
                if (response.get(BackEnd.Response.DATA) instanceof JSONObject) {
                    //Data is Object
                    onSuccessResponse(response.getJSONObject(BackEnd.Response.DATA), meta);
                } else {
                    //Data is Array
                    onSuccessResponse(response.getJSONArray(BackEnd.Response.DATA), meta);
                }
            } else {
                onErrorResponse(meta);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionProgress(int progress) {

    }

    @Override
    public Context getListenerContext() {
        return this;
    }

}
