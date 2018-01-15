package com.vibecampo.android.feed.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.vibecampo.android.App;
import com.vibecampo.android.BuildConfig;
import com.vibecampo.android.R;
import com.vibecampo.android.activity.FragmentActivity;
import com.vibecampo.android.feed.cell.FeedCell;
import com.vibecampo.android.fragment.UsersFragment;
import com.vibecampo.android.goals.fragment.GoalsFragment;
import com.vibecampo.android.jobqueue.SendLikeJob;
import com.vibecampo.android.model.Community;
import com.vibecampo.android.model.Feed;
import com.vibecampo.android.model.User;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;
import com.vibecampo.android.view.FeedView;

import ke.co.toshngure.basecode.dataloading.DataLoadingConfig;
import ke.co.toshngure.basecode.dataloading.ModelListFragment;

public class FeedFragment extends ModelListFragment<Feed, FeedCell> implements FeedView.FeedListener {


    public static final String ARG_COMMUNITY = "arg_community";
    private Community mCommunity;
    private User mUser;
    private int mLoaderId = 0;
    private int mFeedFragmentType;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance() {
        Bundle args = new Bundle();
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static FeedFragment newInstance(Community community) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_COMMUNITY, community);
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static FeedFragment newInstance(User user) {
        Bundle args = new Bundle();
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunity = getArguments().getParcelable(ARG_COMMUNITY);
    }

    @Override
    protected FeedCell onCreateCell(Feed feed) {
        FeedCell feedCell = new FeedCell(feed);
        feedCell.setFeedListener(this);
        return feedCell;
    }

    @Override
    protected DataLoadingConfig getDataLoadingConfig() {
        return new DataLoadingConfig()
                .setDebugEnabled(BuildConfig.DEBUG)
                .setCacheEnabled(false)
                .setPerPage(10)
                //.setLoaderId(mLoaderId)
                .setUrl(Client.absoluteUrl(BackEnd.EndPoints.REQUEST));
    }

    @Override
    protected Class<Feed> getModelClass() {
        return Feed.class;
    }

    @Override
    protected AsyncHttpClient getClient() {
        return Client.getInstance().getClient();
    }

    @Override
    protected RequestParams getRequestParams() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(BackEnd.Params.QUERY, BackEnd.QUERY.GET_VIBES);
        requestParams.put(BackEnd.Params.PUBLISHER_ID, mUser == null ? 0 : 1);

        if (mCommunity != null) {
            requestParams.put(BackEnd.Params.TO_COMMUNITY, 1);
            requestParams.put(BackEnd.Params.RECIPIENT_ID, mCommunity.getId());
        } else {
            requestParams.put(BackEnd.Params.TO_COMMUNITY, 0);
            requestParams.put(BackEnd.Params.RECIPIENT_ID, 0);
        }

        if (mUser != null) {
            requestParams.put(BackEnd.Params.RECIPIENT_ID, mUser.getUserId());
        }

        requestParams.put(BackEnd.Params.GOAL_ID, 0);

        return requestParams;
    }

    @Override
    protected void setUpTopView(FrameLayout topViewContainer) {
        super.setUpTopView(topViewContainer);
        if (mCommunity != null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_feed_community_top_view, null);
            topViewContainer.addView(view);

            TextView aboutTV = view.findViewById(R.id.aboutTV);
            aboutTV.setText(mCommunity.getAbout());

            TextView membersCountTV = view.findViewById(R.id.membersCountTV);
            membersCountTV.setText("0 Members");
            membersCountTV.setOnClickListener(view1 -> FragmentActivity.start(getActivity(),
                    UsersFragment.newInstance(), mCommunity.getName(), "Members"));

            TextView goalsCountTV = view.findViewById(R.id.goalsCountTV);
            goalsCountTV.setText("0 Goals");
            goalsCountTV.setOnClickListener(view2 -> FragmentActivity.start(getActivity(),
                    GoalsFragment.newInstance(false), mCommunity.getName(), "Goals"));

            TextView joinTV = view.findViewById(R.id.joinTV);
            joinTV.setOnClickListener(view3 -> {

            });
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisible() && isVisibleToUser) {
            //toastDebug("FeedFragmentType = " + mFeedFragmentType);
        }
    }

    @Override
    public void onFeedLiked(Feed feed, int position) {
        if (feed.getUserLiked()) {
            feed.setUserLiked(false);
            feed.getEngagements().setLikes(feed.getEngagements().getLikes() - 1);
        } else {
            feed.setUserLiked(true);
            feed.getEngagements().setLikes(feed.getEngagements().getLikes() + 1);
        }
        getSimpleRecyclerView().updateCell(position, feed);
        App.getInstance().getJobManager().addJobInBackground(new SendLikeJob(feed.getId()));
    }
}
