package com.vibecampo.android.goals.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.vibecampo.android.R;
import com.vibecampo.android.activity.FragmentActivity;
import com.vibecampo.android.communities.fragment.CommunitiesFragment;
import com.vibecampo.android.goals.cell.GoalCell;
import com.vibecampo.android.model.Community;
import com.vibecampo.android.model.Goal;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;

import ke.co.toshngure.basecode.dataloading.DataLoadingConfig;
import ke.co.toshngure.basecode.dataloading.ModelListBottomSheetFragment;

/**
 * Created by Anthony Ngure on 24/11/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class GoalsFragment extends ModelListBottomSheetFragment<Goal, GoalCell>
        implements SimpleCell.OnCellClickListener<Goal> {

    private static final String ARG_WITH_TOP_VIEW = "arg_with_top_view";
    private boolean mWithTopView = true;

    public static GoalsFragment newInstance(boolean withTopView) {

        Bundle args = new Bundle();
        args.putBoolean(ARG_WITH_TOP_VIEW, withTopView);
        GoalsFragment fragment = new GoalsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static GoalsFragment newInstance() {

        Bundle args = new Bundle();

        GoalsFragment fragment = new GoalsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWithTopView = getArguments().getBoolean(ARG_WITH_TOP_VIEW, true);
    }

    @Override
    protected GoalCell onCreateCell(Goal goal) {
        GoalCell goalCell = new GoalCell(goal);
        goalCell.setOnCellClickListener(this);
        return goalCell;
    }

    @Override
    protected DataLoadingConfig getDataLoadingConfig() {
        return new DataLoadingConfig()
                .setCacheEnabled(false)
                .setCursorsEnabled(false)
                .setRefreshEnabled(false)
                .setLoadingMoreEnabled(false)
                .setUrl(Client.absoluteUrl(BackEnd.EndPoints.REQUEST));
    }

    @Override
    protected RequestParams getRequestParams() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(BackEnd.Params.PURPOSE, 1);
        requestParams.put(BackEnd.Params.MY_GOALS, 1);
        requestParams.put(BackEnd.Params.QUERY, BackEnd.QUERY.GET_GOALS_TAB);
        return requestParams;
    }

    @Override
    protected AsyncHttpClient getClient() {
        return Client.getInstance().getClient();
    }

    @Override
    protected Class<Goal> getModelClass() {
        return Goal.class;
    }

    @Override
    protected void setUpTopView(FrameLayout topViewContainer) {
        super.setUpTopView(topViewContainer);
        if (mWithTopView){
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goals_top_view, null);
            topViewContainer.addView(view);
            view.findViewById(R.id.createGoalBtn).setOnClickListener(view1 -> FragmentActivity.start(getContext(),
                    CommunitiesFragment.newInstance(CommunitiesFragment.CREATE_GOAL),
                    "Select a community"));
        }
    }

    @Override
    protected void setUpSimpleRecyclerView(SimpleRecyclerView simpleRecyclerView) {
        super.setUpSimpleRecyclerView(simpleRecyclerView);
        simpleRecyclerView.showDivider();
        SimpleSectionHeaderProvider<Goal> headerProvider = new SimpleSectionHeaderProvider<Goal>() {
            @NonNull
            @Override
            public View getSectionHeaderView(@NonNull Goal goal, int i) {
                TextView textView = ((TextView) LayoutInflater.from(getActivity()).inflate(R.layout.cell_goal_header, null));
                textView.setText(goal.getMyGoal() ? "My goals" : "Suggested goals");
                textView.setPadding(10, 10, 10, 10);
                return textView;
            }

            @Override
            public boolean isSameSection(@NonNull Goal goal, @NonNull Goal nextGoal) {
                return goal.getMyGoal() == nextGoal.getMyGoal();
            }
        };
        simpleRecyclerView.setSectionHeader(headerProvider);
    }

    @Override
    public void onCellClicked(@NonNull Goal goal) {
        Community community = new Community();
        community.setId(goal.getCommunityId());
        community.setName(goal.getCommunityName());
        FragmentActivity.start(getContext(), GoalPageFragment.newInstance(community, goal), goal.getCommunityName());
    }
}
