package com.vibecampo.android.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vibecampo.android.BuildConfig;
import com.vibecampo.android.R;
import com.vibecampo.android.activity.FragmentActivity;
import com.vibecampo.android.feed.activity.FeedViewActivity;
import com.vibecampo.android.feed.fragment.CommentsFragment;
import com.vibecampo.android.model.Feed;
import com.vibecampo.android.network.BackEnd;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.basecode.images.CollageView;
import ke.co.toshngure.basecode.images.NetworkImage;
import ke.co.toshngure.basecode.utils.BaseUtils;
import ke.co.toshngure.basecode.utils.DatesHelper;
import ke.co.toshngure.basecode.view.MaterialTextView;

/**
 * Created by Anthony Ngure on 03/11/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class FeedView extends FrameLayout {

    public static final int MAX_DISPLAY_TEXT_LENGTH = 400;

    @BindView(R.id.publisherAvatarNI)
    NetworkImage publisherAvatarNI;
    @BindView(R.id.storyTypeTV)
    TextView storyTypeTV;
    @BindView(R.id.publisherNameTV)
    TextView publisherNameTV;
    @BindView(R.id.communityNameTV)
    TextView recipientNameTV;
    @BindView(R.id.createdAtTV)
    MaterialTextView createdAtTV;
    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.titleView)
    LinearLayout titleView;
    @BindView(R.id.textTV)
    MaterialTextView textTV;
    @BindView(R.id.collageView)
    CollageView collageView;
    @BindView(R.id.infoTV)
    TextView infoTV;
    @BindView(R.id.itemSeparator)
    View itemSeparator;
    @BindView(R.id.likesCountTV)
    MaterialTextView likesCountTV;
    @BindView(R.id.likesCountView)
    FrameLayout likesCountView;
    @BindView(R.id.commentsCountTV)
    MaterialTextView commentsCountTV;
    @BindView(R.id.commentsCountView)
    FrameLayout commentsCountView;
    @BindView(R.id.revibeView)
    FrameLayout revibeView;

    @BindDrawable(R.drawable.ic_favorite_border_black_24dp)
    Drawable unfavoriteDrawable;
    @BindDrawable(R.drawable.ic_favorite_black_24dp)
    Drawable favouriteDrawable;

    private Feed feed;
    private boolean limitText;

    private FeedListener feedListener;
    private int position;


    public FeedView(@NonNull Context context) {
        this(context, null);
    }

    public FeedView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FeedView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_feed, this, true);
        ButterKnife.bind(this);
    }

    public void bind(Feed item, FeedListener feedListener) {
        bind(item, false, 0, feedListener);
    }

    @SuppressLint("SetTextI18n")
    public void bind(Feed item, boolean limitTxt, int position, FeedListener feedListener) {
        this.feed = item;
        this.limitText = limitTxt;
        this.position = position;
        this.feedListener = feedListener;

        collageView.setOnPhotoClickListener(i -> {
            if (FeedView.this.limitText) {
                FeedViewActivity.start(getContext(), this.feed);
            }
        });

        publisherAvatarNI.loadImageFromNetwork(item.getPublisher().getAvatar());
        if (item.getRecipient() != null) {
            publisherNameTV.setText(item.getPublisher().getFullName());
            recipientNameTV.setText("to " + item.getRecipient().getName());
            recipientNameTV.setVisibility(View.VISIBLE);
        } else {
            publisherNameTV.setText(item.getPublisher().getFullName());
            recipientNameTV.setVisibility(View.GONE);
        }

        createdAtTV.setReferenceTime(DatesHelper.formatSqlTimestamp(item.getTimestamp()));

        if (!TextUtils.isEmpty(item.getTitle())) {
            titleView.setVisibility(View.VISIBLE);
            titleTV.setText(Html.fromHtml(item.getTitle()));
        } else {
            titleView.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(item.getText())) {
            String body;
            if ((item.getText().length() > MAX_DISPLAY_TEXT_LENGTH) && limitTxt) {
                body = Html.fromHtml(getContext().getString(R.string.read_more_format,
                        item.getText().substring(0, MAX_DISPLAY_TEXT_LENGTH))).toString();
            } else {
                body = Html.fromHtml(item.getText()).toString();
            }
            textTV.setLinkEnabledText(body, R.color.colorAccent);
            textTV.setVisibility(View.VISIBLE);
        } else {
            textTV.setVisibility(View.GONE);
        }
        List<String> mediaPaths = new ArrayList<>();
        for (Feed.Media media : item.getMedia()) {
            mediaPaths.add(media.getUrl());
        }
        collageView.loadFromNetwork(mediaPaths);
        storyTypeTV.setText(item.getStoryType());
        commentsCountTV.setText(String.valueOf(item.getEngagements().getComments()));
        likesCountTV.setText(getContext().getString(R.string.counters_format, item.getEngagements().getLikes()));

        if (item.getUserLiked()) {
            likesCountTV.setCompoundDrawablesWithIntrinsicBounds(favouriteDrawable, null, null, null);
            //likesCountTV.setSupportBackgroundTintList(likedColorStateList);
        } else {
            likesCountTV.setCompoundDrawablesWithIntrinsicBounds(unfavoriteDrawable, null, null, null);
            //likesCountTV.setSupportBackgroundTintList(unLikedColorStateList);
        }

        if (BuildConfig.DEBUG) {
            infoTV.setVisibility(View.GONE);
            infoTV.setText("Id = " + item.getId() + " Media = " + item.getMedia().size());
        } else {
            infoTV.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.collageView)
    public void onCollageViewClicked() {
        if (limitText) {
            FeedViewActivity.start(getContext(), this.feed);
        }
    }

    @OnClick(R.id.publisherAvatarNI)
    public void onPublisherAvatarNIClicked() {
    }

    @OnClick(R.id.storyTypeTV)
    public void onStoryTypeTVClicked() {
    }

    @OnClick(R.id.userView)
    public void onUserViewClicked() {

    }

    @OnClick(R.id.textTV)
    public void onTextTVClicked() {
        if (limitText) {
            FeedViewActivity.start(getContext(), this.feed);
        }
    }

    @OnClick(R.id.likesCountView)
    public void onLikesCountViewClicked() {
        this.feedListener.onFeedLiked(this.feed, this.position);
    }


    public interface FeedListener {
        void onFeedLiked(Feed feed, int position);
    }

    @OnClick(R.id.commentsCountView)
    public void onCommentsCountViewClicked() {
        FragmentActivity.start(getContext(), CommentsFragment.newInstance(feed),
                getContext().getString(R.string.comments));
    }

    @OnClick(R.id.revibeView)
    public void onRevibeViewClicked() {
        String text = BackEnd.URL+"/story/"+feed.getId();
        BaseUtils.shareText(getContext(), text);
    }
}
