package com.vibecampo.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vibecampo.android.R;
import com.vibecampo.android.feed.activity.PostVibeActivity;
import com.vibecampo.android.model.Community;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Anthony Ngure on 29/10/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class ChooseWhatToPostDialog extends BottomSheetDialogFragment {

    public static final String ARG_COMMUNITY = "arg_community";

    Unbinder unbinder;
    private Community mCommunity;

    public static ChooseWhatToPostDialog newInstance(Community community) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_COMMUNITY, community);
        ChooseWhatToPostDialog fragment = new ChooseWhatToPostDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunity = getArguments().getParcelable(ARG_COMMUNITY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_what_to_post, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.showcaseSLIV, R.id.questionSLIV, R.id.tipsSLIV, R.id.newsSLIV, R.id.opportunitySLIV})
    public void onViewClicked(View view) {
        String storyType;
        switch (view.getId()) {
            case R.id.showcaseSLIV:
                storyType = "Me";
                break;
            case R.id.questionSLIV:
                storyType = "Question";
                break;
            case R.id.tipsSLIV:
                storyType = "Tips";
                break;
            case R.id.newsSLIV:
                storyType = "News";
                break;
            case R.id.opportunitySLIV:
                storyType = "Opportunity";
                break;
            default:
                storyType = "Me";
        }
        PostVibeActivity.start(getActivity(), mCommunity, storyType);
        this.dismiss();
        getActivity().finish();
    }
}
