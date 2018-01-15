package com.vibecampo.android.communities.fragment;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.vibecampo.android.BuildConfig;
import com.vibecampo.android.R;
import com.vibecampo.android.activity.FragmentActivity;
import com.vibecampo.android.communities.cell.CommunityCell;
import com.vibecampo.android.feed.fragment.FeedFragment;
import com.vibecampo.android.fragment.ChooseWhatToPostDialog;
import com.vibecampo.android.goals.activity.CreateGoalActivity;
import com.vibecampo.android.model.Community;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ke.co.toshngure.basecode.dataloading.DataLoadingConfig;
import ke.co.toshngure.basecode.dataloading.ModelListFragment;

/**
 * Created by Anthony Ngure on 27/10/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class CommunitiesFragment extends ModelListFragment<Community, CommunityCell>
        implements SimpleCell.OnCellClickListener<Community> {

    public static final int COMMUNITIES_TAB = 0;
    public static final int POST_TO_COMMUNITY = 1;
    public static final int CREATE_GOAL = 2;

    public static final String ARG_PURPOSE = "arg_purpose";

    private int mPurpose;

    @IntDef({CommunitiesFragment.COMMUNITIES_TAB,
            CommunitiesFragment.CREATE_GOAL,
            CommunitiesFragment.POST_TO_COMMUNITY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CommunitiesListPurpose {
    }

    public static CommunitiesFragment newInstance(@CommunitiesListPurpose int purpose) {
        Bundle args = new Bundle();
        CommunitiesFragment fragment = new CommunitiesFragment();
        args.putInt(ARG_PURPOSE, purpose);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPurpose = getArguments().getInt(ARG_PURPOSE, COMMUNITIES_TAB);
    }

    @Override
    protected CommunityCell onCreateCell(Community community) {
        CommunityCell communityCell = new CommunityCell(community, mPurpose);
        communityCell.setOnCellClickListener(this);
        return communityCell;
    }

    @Override
    protected DataLoadingConfig getDataLoadingConfig() {
        return new DataLoadingConfig()
                .setRefreshEnabled(false)
                .setCursorsEnabled(false)
                .setCacheEnabled(false)
                .setLoadingMoreEnabled(false)
                .setDebugEnabled(BuildConfig.DEBUG)
                .setUrl(Client.absoluteUrl(BackEnd.EndPoints.REQUEST));
    }

    @Override
    protected RequestParams getRequestParams() {
        RequestParams requestParams = new RequestParams();
        if (mPurpose == POST_TO_COMMUNITY || mPurpose == CREATE_GOAL) {
            requestParams.put(BackEnd.Params.QUERY, BackEnd.QUERY.GET_USER_COMMUNITIES);
        } else {
            requestParams.put(BackEnd.Params.QUERY, BackEnd.QUERY.GET_COMMUNITIES_TAB);
        }
        requestParams.put(BackEnd.Params.LIMIT, 15);
        requestParams.put(BackEnd.Params.PURPOSE, 1);
        return requestParams;
    }

    @Override
    protected AsyncHttpClient getClient() {
        return Client.getInstance().getClient();
    }

    @Override
    protected Class<Community> getModelClass() {
        return Community.class;
    }

    @Override
    public void onCellClicked(@NonNull Community community) {
        switch (mPurpose) {
            case POST_TO_COMMUNITY:
                ChooseWhatToPostDialog.newInstance(community).show(getChildFragmentManager(), "dialog");
                break;
            case CREATE_GOAL:
                CreateGoalActivity.start(getContext(), community);
                getActivity().finish();
                break;
            case COMMUNITIES_TAB:
            default:
                FragmentActivity.start(getContext(), FeedFragment.newInstance(community), community.getName());
        }
    }

    @Override
    protected void setUpSimpleRecyclerView(SimpleRecyclerView simpleRecyclerView) {
        super.setUpSimpleRecyclerView(simpleRecyclerView);
        if (mPurpose == COMMUNITIES_TAB) {
            SimpleSectionHeaderProvider<Community> headerProvider = new SimpleSectionHeaderProvider<Community>() {
                @NonNull
                @Override
                public View getSectionHeaderView(@NonNull Community community, int i) {
                    TextView textView = ((TextView) LayoutInflater.from(getActivity()).inflate(R.layout.cell_goal_header, null));
                    textView.setText(community.getIsMember() ? "My communities" : "Suggested communities");
                    textView.setPadding(10, 10, 10, 10);
                    return textView;
                }

                @Override
                public boolean isSameSection(@NonNull Community community, @NonNull Community nextCommunity) {
                    return community.getIsMember() == nextCommunity.getIsMember();
                }
            };
            simpleRecyclerView.setSectionHeader(headerProvider);
        } else {
            simpleRecyclerView.showDivider();
        }

    }
}
