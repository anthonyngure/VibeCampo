package com.vibecampo.android.notifications.fragment;

import android.os.Bundle;

import com.jaychang.srv.SimpleRecyclerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.vibecampo.android.model.Notification;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;
import com.vibecampo.android.notifications.cell.NotificationCell;

import ke.co.toshngure.basecode.dataloading.DataLoadingConfig;
import ke.co.toshngure.basecode.dataloading.ModelListFragment;

/**
 * Created by Anthony Ngure on 29/11/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class NotificationsFragment extends ModelListFragment<Notification, NotificationCell> {

    public static NotificationsFragment newInstance() {

        Bundle args = new Bundle();

        NotificationsFragment fragment = new NotificationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected NotificationCell onCreateCell(Notification notification) {
        return new NotificationCell(notification);
    }

    @Override
    protected DataLoadingConfig getDataLoadingConfig() {
        return new DataLoadingConfig()
                .setCursorsEnabled(false)
                .setRefreshEnabled(false)
                .setLoadingMoreEnabled(false)
                .setCacheEnabled(false)
                .setUrl(Client.absoluteUrl(BackEnd.EndPoints.REQUEST));
    }

    @Override
    protected AsyncHttpClient getClient() {
        return Client.getInstance().getClient();
    }

    @Override
    protected Class<Notification> getModelClass() {
        return Notification.class;
    }

    @Override
    protected RequestParams getRequestParams() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(BackEnd.Params.QUERY, BackEnd.QUERY.GET_NOTIFICATIONS);
        return requestParams;
    }

    @Override
    protected void setUpSimpleRecyclerView(SimpleRecyclerView simpleRecyclerView) {
        super.setUpSimpleRecyclerView(simpleRecyclerView);
        simpleRecyclerView.showDivider();
    }
}
