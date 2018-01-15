package com.vibecampo.android.communities.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;
import com.vibecampo.android.R;
import com.vibecampo.android.communities.fragment.CommunitiesFragment;
import com.vibecampo.android.model.Community;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.toshngure.basecode.images.NetworkImage;

/**
 * Created by Anthony Ngure on 27/10/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class CommunityCell extends SimpleCell<Community, CommunityCell.CommunityViewHolder> {
    private int mCommunityPurpose;

    public CommunityCell(@NonNull Community item, int communityPurpose) {
        super(item);
        this.mCommunityPurpose = communityPurpose;
    }

    @Override
    protected int getLayoutRes() {
        if (mCommunityPurpose == CommunitiesFragment.POST_TO_COMMUNITY
                || mCommunityPurpose == CommunitiesFragment.CREATE_GOAL) {
            return R.layout.cell_community_no_card;
        } else {
            return R.layout.cell_community;
        }

    }

    @NonNull
    @Override
    protected CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return new CommunityViewHolder(view, mCommunityPurpose);
    }

    @Override
    protected void onBindViewHolder(@NonNull CommunityViewHolder communityViewHolder, int i, @NonNull Context context, Object o) {
        communityViewHolder.bind(getItem());
    }

    static class CommunityViewHolder extends SimpleViewHolder {

        @Nullable
        @BindView(R.id.avatarNI)
        NetworkImage avatarNI;

        @BindView(R.id.nameTV)
        TextView nameTV;


        @Nullable
        @BindView(R.id.aboutTV)
        TextView aboutTV;

        private int communityPurpose;

        CommunityViewHolder(@NonNull View itemView, int communityPurpose) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.communityPurpose = communityPurpose;
        }

        void bind(Community community) {
            if (communityPurpose == CommunitiesFragment.POST_TO_COMMUNITY
                    || communityPurpose == CommunitiesFragment.CREATE_GOAL) {
                nameTV.setText(community.getName());
            } else {
                nameTV.setText(community.getName());
                if (avatarNI != null) {
                    avatarNI.loadImageFromNetwork(community.getActualCoverUrl());
                }
                if (aboutTV != null) {
                    aboutTV.setText(Html.fromHtml(community.getAbout()));
                }
            }
        }
    }
}
