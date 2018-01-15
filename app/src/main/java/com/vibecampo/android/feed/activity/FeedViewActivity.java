package com.vibecampo.android.feed.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.vibecampo.android.App;
import com.vibecampo.android.R;
import com.vibecampo.android.activity.BaseActivity;
import com.vibecampo.android.jobqueue.SendLikeJob;
import com.vibecampo.android.model.Feed;
import com.vibecampo.android.view.FeedView;

public class FeedViewActivity extends BaseActivity implements FeedView.FeedListener {

    private static final String EXTRA_FEED = "extra_feed";
    private Feed mFeed;
    private FeedView mFeedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_view);

        mFeed = getIntent().getExtras().getParcelable(EXTRA_FEED);

        mFeedView = findViewById(R.id.feedView);
        mFeedView.bind(mFeed, this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void start(Context context, Feed feed) {
        Intent starter = new Intent(context, FeedViewActivity.class);
        starter.putExtra(EXTRA_FEED, feed);
        context.startActivity(starter);
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
        mFeedView.bind(feed, this);
        App.getInstance().getJobManager().addJobInBackground(new SendLikeJob(feed.getId()));
    }
}
