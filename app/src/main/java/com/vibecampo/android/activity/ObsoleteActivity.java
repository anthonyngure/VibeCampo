/*
 * Copyright (c) 2016. VibeCampo Social Network
 *
 * Website : http://www.vibecampo.com
 */

package com.vibecampo.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.vibecampo.android.R;
import com.vibecampo.android.utils.FirebaseUtils;

public class ObsoleteActivity extends BaseActivity {


    public static final String PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=com.vibecampo.android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obsolete);
        String updatedAt = FirebaseRemoteConfig.getInstance().getString(FirebaseUtils.RemoteConfigParams.UPDATED_AT);
        ((TextView) findViewById(R.id.info)).setText(
                Html.fromHtml(String.format(getString(R.string.obsolete_info),
                        getUser().getFullName(), updatedAt))
        );

    }

    public void downloadNow(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(PLAY_STORE_LINK));
        startActivity(i);
    }

    public void downloadLater(View view) {
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
