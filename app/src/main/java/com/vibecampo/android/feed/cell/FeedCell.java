package com.vibecampo.android.feed.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;
import com.jaychang.srv.Updatable;
import com.vibecampo.android.R;
import com.vibecampo.android.model.Feed;
import com.vibecampo.android.view.FeedView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anthony Ngure on 28/10/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class FeedCell extends SimpleCell<Feed, FeedCell.FeedViewHolder> implements Updatable<Feed> {

    private FeedView.FeedListener feedListener;

    public FeedCell(@NonNull Feed item) {
        super(item);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.cell_feed;
    }

    @NonNull
    @Override
    protected FeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return new FeedViewHolder(view, feedListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull FeedViewHolder feedViewHolder, int position, @NonNull Context context, Object o) {
        feedViewHolder.bind(getItem(), position);
    }

    public void setFeedListener(FeedView.FeedListener feedListener) {
        this.feedListener = feedListener;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Feed feed) {
        return getItem().getUserLiked() == feed.getUserLiked();
    }

    @Override
    public Object getChangePayload(@NonNull Feed feed) {
        return feed;
    }

    static class FeedViewHolder extends SimpleViewHolder {

        @BindView(R.id.feedView)
        FeedView feedView;
        private FeedView.FeedListener feedListener;

        FeedViewHolder(@NonNull View itemView, FeedView.FeedListener feedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.feedListener = feedListener;
        }

        void bind(Feed feed, int position){
            feedView.bind(feed, true, position, feedListener);
        }

    }

}
