package com.vibecampo.android.notifications.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;
import com.vibecampo.android.R;
import com.vibecampo.android.model.Notification;

/**
 * Created by Anthony Ngure on 29/11/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class NotificationCell extends SimpleCell<Notification, NotificationCell.NotificationViewHolder> {
    public NotificationCell(@NonNull Notification item) {
        super(item);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.cell_notification;
    }

    @NonNull
    @Override
    protected NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return new NotificationViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i, @NonNull Context context, Object o) {

    }

    public class NotificationViewHolder extends SimpleViewHolder {
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
