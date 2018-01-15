package com.vibecampo.android.feed.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.jaychang.srv.SimpleRecyclerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vibecampo.android.R;
import com.vibecampo.android.feed.cell.CommentCell;
import com.vibecampo.android.model.Comment;
import com.vibecampo.android.model.Feed;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;

import ke.co.toshngure.basecode.dataloading.DataLoadingConfig;
import ke.co.toshngure.basecode.dataloading.ModelListFragment;
import ke.co.toshngure.basecode.utils.BaseUtils;

/**
 * Created by Anthony Ngure on 24/11/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class CommentsFragment extends ModelListFragment<Comment, CommentCell> {

    private static final String ARG_FEED = "arg_feed";
    private Feed mFeed;
    private ImageButton mSendBtn;
    private EmojiEditText mEditText;
    private EmojiPopup mEmojiPopup;
    private ImageButton emojiBtn;


    public static CommentsFragment newInstance(Feed feed) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_FEED, feed);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeed = getArguments().getParcelable(ARG_FEED);
    }

    @Override
    protected CommentCell onCreateCell(Comment comment) {
        return new CommentCell(comment);
    }

    @Override
    protected DataLoadingConfig getDataLoadingConfig() {
        return new DataLoadingConfig()
                .setPerPage(10)
                .setCursorsEnabled(false)
                .setLoadingMoreEnabled(false)
                .setUrl(Client.absoluteUrl(BackEnd.EndPoints.REQUEST));
    }

    @Override
    protected RequestParams getRequestParams() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(BackEnd.Params.QUERY, BackEnd.QUERY.GET_VIBE_COMMENTS);
        requestParams.put(BackEnd.Params.VIBE_ID, mFeed.getId());
        return requestParams;
    }

    @Override
    protected AsyncHttpClient getClient() {
        return Client.getInstance().getClient();
    }

    @Override
    protected Class<Comment> getModelClass() {
        return Comment.class;
    }

    @Override
    protected void setUpSimpleRecyclerView(SimpleRecyclerView simpleRecyclerView) {
        super.setUpSimpleRecyclerView(simpleRecyclerView);
        simpleRecyclerView.showDivider();
    }

    @Override
    protected void setUpBottomView(FrameLayout bottomViewContainer) {
        super.setUpBottomView(bottomViewContainer);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_comments_bottom_view, null);
        emojiBtn = view.findViewById(R.id.emojiButton);
        mSendBtn = view.findViewById(R.id.sendButton);
        mEditText = view.findViewById(R.id.editText);
        bottomViewContainer.addView(view);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSendBtn.setVisibility(s.toString().trim().length() == 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mSendBtn.setOnClickListener(view1 -> handleText());

        mEditText.setEmojiSize(BaseUtils.dpToPx(18));
        mEmojiPopup = EmojiPopup.Builder.fromRootView(bottomViewContainer.getRootView())
                .setOnEmojiPopupShownListener(() -> emojiBtn.setImageResource(R.drawable.ic_keyboard_black_24dp))
                .setOnEmojiPopupDismissListener(() -> emojiBtn.setImageResource(R.drawable.ic_insert_emoticon_black_24dp))
                .build(mEditText);
        emojiBtn.setOnClickListener(view2 -> mEmojiPopup.toggle());
    }

    private void handleText(){
        String text = mEditText.getText().toString();
        mEditText.getText().clear();
    }
}
