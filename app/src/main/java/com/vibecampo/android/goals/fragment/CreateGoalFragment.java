package com.vibecampo.android.goals.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.vibecampo.android.R;
import com.vibecampo.android.activity.FragmentActivity;
import com.vibecampo.android.fragment.BaseFragment;
import com.vibecampo.android.model.Community;
import com.vibecampo.android.model.Goal;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;
import com.vibecampo.android.network.Meta;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ke.co.toshngure.basecode.networking.ResponseHandler;
import ke.co.toshngure.basecode.utils.BaseUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGoalFragment extends BaseFragment {

    private static final String ARG_COMMUNITY = "arg_community";
    @BindView(R.id.headerTV)
    TextView headerTV;
    @BindView(R.id.nameMET)
    MaterialEditText nameMET;
    @BindView(R.id.descriptionMET)
    MaterialEditText descriptionMET;
    @BindView(R.id.continueBtn)
    Button continueBtn;
    Unbinder unbinder;
    private Community mCommunity;

    public CreateGoalFragment() {
        // Required empty public constructor
    }

    public static CreateGoalFragment newInstance(Community community) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_COMMUNITY, community);
        CreateGoalFragment fragment = new CreateGoalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunity = getArguments().getParcelable(ARG_COMMUNITY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_goal, container, false);
        unbinder = ButterKnife.bind(this, view);
        headerTV.setText(mCommunity.getName());
        nameMET.addValidator(BaseUtils.createRequiredValidator(getString(R.string.error_field_required)));
        descriptionMET.addValidator(BaseUtils.createRequiredValidator(getString(R.string.error_field_required)));
        continueBtn.setOnClickListener(view1 -> {
            if (nameMET.validate() && descriptionMET.validate()) {
                connect();
            }
        });
        return view;
    }

    @Override
    public void connect() {
        super.connect();
        RequestParams params = new RequestParams();
        params.put(BackEnd.Params.QUERY, BackEnd.QUERY.ADD_GOAL);
        params.put(BackEnd.Params.GOAL_NAME, BackEnd.QUERY.ADD_GOAL);
        params.put(BackEnd.Params.GOAL_NAME, nameMET.getText().toString());
        params.put(BackEnd.Params.GOAL_DESCRIPTION, descriptionMET.getText().toString());
        params.put(BackEnd.Params.COMMUNITY_ID, mCommunity.getId());
        Client.getInstance().getClient().get(Client.absoluteUrl(BackEnd.EndPoints.REQUEST),
                params, new ResponseHandler(this));
    }

    @Override
    protected void onSuccessResponse(JSONObject data, Meta meta) {
        super.onSuccessResponse(data, meta);
        Goal goal = BaseUtils.getSafeGson().fromJson(data.toString(), Goal.class);
        FragmentActivity.start(getContext(), GoalPageFragment.newInstance(mCommunity, goal), goal.getGoalName());
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
