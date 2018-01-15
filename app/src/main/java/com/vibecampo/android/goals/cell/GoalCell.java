package com.vibecampo.android.goals.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;
import com.vibecampo.android.R;
import com.vibecampo.android.model.Goal;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anthony Ngure on 24/11/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class GoalCell extends SimpleCell<Goal, GoalCell.GoalViewHolder> {

    public GoalCell(@NonNull Goal item) {
        super(item);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.cell_goal;
    }

    @NonNull
    @Override
    protected GoalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return new GoalViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull GoalViewHolder goalViewHolder, int i, @NonNull Context context, Object o) {
        goalViewHolder.bind(getItem(), context);
    }

    static class GoalViewHolder extends SimpleViewHolder {
        @BindView(R.id.nameTV)
        TextView nameTV;
        @BindView(R.id.descriptionTV)
        TextView descriptionTV;

        GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Goal item, Context context) {
            nameTV.setText(Html.fromHtml(item.getGoalName()));
            descriptionTV.setText(Html.fromHtml(item.getGoalDescription()));
        }
    }
}
