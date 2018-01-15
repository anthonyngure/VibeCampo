package com.vibecampo.android.goals.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jaychang.srv.SimpleRecyclerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.vibecampo.android.R;
import com.vibecampo.android.cell.UserCell;
import com.vibecampo.android.feed.activity.PostVibeActivity;
import com.vibecampo.android.model.Community;
import com.vibecampo.android.model.Goal;
import com.vibecampo.android.model.User;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;

import ke.co.toshngure.basecode.dataloading.DataLoadingConfig;
import ke.co.toshngure.basecode.dataloading.ModelListFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalPageFragment extends ModelListFragment<User, UserCell> {


    private static final String ARG_GOAL = "arg_goal";
    private static final String ARG_COMMUNITY = "arg_community";
    private Goal mGoal;
    private Community mCommunity;

    public GoalPageFragment() {
        // Required empty public constructor
    }

    public static GoalPageFragment newInstance(Community community, Goal goal) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_GOAL, goal);
        args.putParcelable(ARG_COMMUNITY, community);
        GoalPageFragment fragment = new GoalPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoal = getArguments().getParcelable(ARG_GOAL);
        mCommunity = getArguments().getParcelable(ARG_COMMUNITY);
    }

    @Override
    protected UserCell onCreateCell(User user) {
        return new UserCell(user);
    }

    @Override
    protected DataLoadingConfig getDataLoadingConfig() {
        return new DataLoadingConfig()
                .setCacheEnabled(false)
                .setCursorsEnabled(false)
                .setUrl(Client.absoluteUrl(BackEnd.EndPoints.REQUEST));
    }

    @Override
    protected AsyncHttpClient getClient() {
        return Client.getInstance().getClient();
    }

    @Override
    protected RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        return params;
    }

    @Override
    protected Class<User> getModelClass() {
        return User.class;
    }

    @Override
    protected void setUpSimpleRecyclerView(SimpleRecyclerView simpleRecyclerView) {
        super.setUpSimpleRecyclerView(simpleRecyclerView);
        simpleRecyclerView.showDivider();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setUpTopView(FrameLayout topViewContainer) {
        super.setUpTopView(topViewContainer);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goal_page_top_view, null);
        topViewContainer.addView(view);

        TextView nameTV = view.findViewById(R.id.nameTV);
        nameTV.setText(mGoal.getGoalName());

        TextView publisherNameTV = view.findViewById(R.id.publisherNameTV);
        publisherNameTV.setText("By " + mGoal.getFirstName() + " " + mGoal.getLastName());

        TextView descriptionTV = view.findViewById(R.id.descriptionTV);
        descriptionTV.setText(Html.fromHtml(mGoal.getGoalDescription()));

        TextView vibersCountBtn = view.findViewById(R.id.vibersCountTV);
        vibersCountBtn.setText("0 Vibers");

        TextView checkInsCountBtn = view.findViewById(R.id.checkInsCountTV);
        checkInsCountBtn.setText("0 Check-In (s)");

        view.findViewById(R.id.checkInTV).setOnClickListener(view1 -> {
            PostVibeActivity.start(getContext(), mCommunity, mGoal);
        });
    }
}
