package com.vibecampo.android.feed.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;
import com.vibecampo.android.R;
import com.vibecampo.android.model.Comment;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.toshngure.basecode.images.NetworkImage;
import ke.co.toshngure.basecode.view.MaterialTextView;

/**
 * Created by Anthony Ngure on 24/11/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class CommentCell extends SimpleCell<Comment, CommentCell.CommentViewHolder> {
    public CommentCell(@NonNull Comment item) {
        super(item);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.cell_comment;
    }

    @NonNull
    @Override
    protected CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return new CommentViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i, @NonNull Context context, Object o) {
        commentViewHolder.bind(getItem(), context);
    }

    static class CommentViewHolder extends SimpleViewHolder {

        @BindView(R.id.publisherAvatarNI)
        NetworkImage publisherAvatarNI;
        @BindView(R.id.publisherNameTV)
        TextView publisherNameTV;
        @BindView(R.id.likesCountTV)
        TextView likesCountTV;
        @BindView(R.id.textTV)
        MaterialTextView textTV;
        @BindView(R.id.createdAtTV)
        MaterialTextView createdAtTV;


        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Comment item, Context context) {
            publisherAvatarNI.loadImageFromNetwork(item.getPublisher().getUseravatar());
            publisherNameTV.setText(context.getString(R.string.name_and_username_format,
                    item.getPublisher().getFullname(), item.getPublisher().getUsername()));
            createdAtTV.setReferenceTime(item.getTime());
            textTV.setText(Html.fromHtml(item.getText()));
            likesCountTV.setText(String.valueOf(item.getEngagements().getLikes()));
        }
    }
}
