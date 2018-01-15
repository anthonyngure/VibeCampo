package com.vibecampo.android.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.loopj.android.http.AsyncHttpClient;
import com.vibecampo.android.cell.UserCell;
import com.vibecampo.android.model.User;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;

import java.util.ArrayList;
import java.util.List;

import ke.co.toshngure.basecode.dataloading.DataLoadingConfig;
import ke.co.toshngure.basecode.dataloading.ModelListFragment;

public class UsersFragment extends ModelListFragment<User, UserCell>
        implements SimpleCell.OnCellClickListener<User> {


    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance() {
        Bundle args = new Bundle();
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected UserCell onCreateCell(User user) {
        UserCell userCell = new UserCell(user);
        userCell.setOnCellClickListener(this);
        return userCell;
    }

    @Override
    protected DataLoadingConfig getDataLoadingConfig() {
        return new DataLoadingConfig()
                .setAutoRefreshEnabled(false)
                .setRefreshEnabled(true)
                .setLoadingMoreEnabled(true)
                .setCacheEnabled(true)
                .setUrl(Client.absoluteUrl(BackEnd.EndPoints.REQUEST));
    }

    @Override
    protected AsyncHttpClient getClient() {
        return Client.getInstance().getClient();
    }

    @Override
    protected Class<User> getModelClass() {
        return User.class;
    }

    @Override
    protected List<User> onLoadCaches() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userList.add(new User());
        }
        return userList;
    }

    @Override
    public void onCellClicked(@NonNull User user) {

    }

    @Override
    protected void setUpSimpleRecyclerView(SimpleRecyclerView simpleRecyclerView) {
        super.setUpSimpleRecyclerView(simpleRecyclerView);
        simpleRecyclerView.showDivider();
    }
}
