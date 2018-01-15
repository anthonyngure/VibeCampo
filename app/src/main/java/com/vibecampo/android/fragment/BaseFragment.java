package com.vibecampo.android.fragment;

import com.vibecampo.android.activity.BaseActivity;
import com.vibecampo.android.model.User;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Meta;
import com.vibecampo.android.settings.PrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ke.co.toshngure.basecode.fragment.BaseAppFragment;
import ke.co.toshngure.basecode.utils.BaseUtils;

/**
 * Created by Anthony Ngure on 11/06/2017.
 * Email : anthonyngure25@gmail.com.
 * Company : VibeCampo Social Network..
 */

public class BaseFragment extends BaseAppFragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    public BaseFragment() {
    }

    protected User getUser() {
        return PrefUtils.getInstance().getUser();
    }

    public void signOut() {
        ((BaseActivity) getActivity()).signOut();
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
        super.onConnectionSuccess(response);
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


    protected boolean isLoggedIn() {
        return ((BaseActivity) getActivity()).isLoggedIn();
    }
}
