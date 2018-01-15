package com.vibecampo.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vibecampo.android.R;
import com.vibecampo.android.activity.FragmentActivity;
import com.vibecampo.android.communities.fragment.CommunitiesFragment;
import com.vibecampo.android.feed.activity.PostVibeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Anthony Ngure on 27/10/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class PostToFragment extends BottomSheetDialogFragment {

    Unbinder unbinder;

    public PostToFragment() {
    }

    public static PostToFragment newInstance() {

        Bundle args = new Bundle();

        PostToFragment fragment = new PostToFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_to, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @OnClick(R.id.toTimelineOption)
    public void onToTimelineOptionClicked() {
        PostVibeActivity.start(getActivity());
        this.dismiss();
    }

    @OnClick(R.id.toCommunityOption)
    public void onToCommunityOptionClicked() {
        FragmentActivity.start(getActivity(), CommunitiesFragment.newInstance(CommunitiesFragment.POST_TO_COMMUNITY),
                getString(R.string.post_to_a_community));
        this.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
