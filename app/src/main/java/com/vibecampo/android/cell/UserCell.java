package com.vibecampo.android.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;
import com.vibecampo.android.R;
import com.vibecampo.android.model.User;

/**
 * Created by Anthony Ngure on 29/10/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class UserCell extends SimpleCell<User, UserCell.UserViewHolder> {

    public UserCell(@NonNull User item) {
        super(item);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.cell_user;
    }

    @NonNull
    @Override
    protected UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return new UserViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position, @NonNull Context context, Object o) {
        userViewHolder.bind(getItem(), context);
    }

    static class UserViewHolder extends SimpleViewHolder {

        private User user;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(User item, Context context) {
            this.user = item;
        }
    }
}
