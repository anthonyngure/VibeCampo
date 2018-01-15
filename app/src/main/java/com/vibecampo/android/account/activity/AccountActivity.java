/*
 * Copyright (c) 2016. VibeCampo Social Network
 *
 * Website : http://www.vibecampo.com
 */

package com.vibecampo.android.account.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;

import com.vibecampo.android.R;
import com.vibecampo.android.activity.BaseActivity;
import com.vibecampo.android.network.Meta;
import com.vibecampo.android.settings.PrefUtils;
import com.vibecampo.android.storage.SimpleStorage;
import com.vibecampo.android.utils.StorageUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.basecode.images.NetworkImage;
import ke.co.toshngure.basecode.images.simplecrop.util.CropUtils;
import ke.co.toshngure.basecode.view.SimpleListItemView;

public class AccountActivity extends BaseActivity {

    private static final String TAG = AccountActivity.class.getSimpleName();

    @BindView(R.id.ppUpdateProgressBar)
    ProgressBar ppUpdateProgressBar;

    @BindView(R.id.avatarPIV)
    NetworkImage mAvatarPIV;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private String ppUpdateImagePath;

    private boolean deletingAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        //Configure Avatar
        mAvatarPIV.setLoadingCallBack(drawable -> {
            /*Bitmap bitmap = ((BitmapDrawable) (drawable.getCurrent())).getBitmap();
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                public void onGenerated(Palette palette) {
                    applyPalette(palette);
                }
            });*/
        });

        mAvatarPIV.loadImageFromNetwork(getUser().getAvatar());
        collapsingToolbarLayout.setTitle(getUser().getFullName());
        getSupportActionBar().setTitle(getUser().getFullName());
        getSupportActionBar().setSubtitle(getUser().getUsername());

        /*if (getUser().getPhone().isEmpty()) {
            ((SimpleListItemView) findViewById(R.id.updatePhoneSIV))
                    .setSubTitle(getString(R.string.add_phone));
        }*/

        if (getUser().getEmail().isEmpty()) {
            ((SimpleListItemView) findViewById(R.id.updateEmailSIV))
                    .setSubTitle(getString(R.string.add_email));
        }
    }


    /*private void applyPalette(Palette palette) {
        int primaryDark = ContextCompat.getColor(this, R.color.colorPrimaryDark);
        int primary = ContextCompat.getColor(this, R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        supportStartPostponedEnterTransition();
    }*/

public static void start(Context context) {
    Intent starter = new Intent(context, AccountActivity.class);
    context.startActivity(starter);
}

    @OnClick(R.id.fab)
    public void handleFab() {
        CropUtils.pickImage(this);
    }

    @OnClick(R.id.profileSIV)
    public void onProfileClick() {
        Intent intent = new Intent(this, ProfileViewActivity.class);
        intent.putExtra(ProfileViewActivity.EXTRA_USER, getUser());
        startActivity(intent);
    }


    @OnClick(R.id.updateUserNameSIV)
    public void updateUserName() {
        //Intent intent = new Intent(this, ProfileItemEditActivity.class);
        //intent.putExtra(ProfileItemEditActivity.EXTRA_EDIT_ITEM, ProfileItemEditActivity.USERNAME);
        //startActivity(intent);
    }

    @OnClick(R.id.updatePhoneSIV)
    public void updatePhone() {
        //Intent intent = new Intent(this, ProfileItemEditActivity.class);
        //intent.putExtra(ProfileItemEditActivity.EXTRA_EDIT_ITEM, ProfileItemEditActivity.PHONE);
        //startActivity(intent);
    }

    @OnClick(R.id.updateEmailSIV)
    public void updateEmail() {
        //Intent intent = new Intent(this, ProfileItemEditActivity.class);
        //intent.putExtra(ProfileItemEditActivity.EXTRA_EDIT_ITEM, ProfileItemEditActivity.EMAIL);
        //startActivity(intent);
    }

    @OnClick(R.id.newsSIV)
    public void seeNews() {
        //startActivity(new Intent(this, NewsActivity.class));
    }

    @OnClick(R.id.reportSIV)
    public void reportProblem() {
        //startActivity(new Intent(this, ReportActivity.class));
    }

    @OnClick(R.id.passwordSIV)
    public void onSavedVibesClick() {
        //startActivity(new Intent(this, ChangePasswordActivity.class));
    }


    @OnClick(R.id.chatPrivacySIV)
    public void onChatPrivacyClick() {
        //startActivity(new Intent(this, ChatPrivacyActivity.class));
    }

    @OnClick(R.id.accountPrivacySIV)
    public void onAccountPrivacyClick() {
        //startActivity(new Intent(this, AccountPrivacyActivity.class));
    }


    @OnClick(R.id.signOutSIV)
    public void onSignOut() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.action_sign_out)
                .setMessage(R.string.guide_sign_out)
                .setPositiveButton(android.R.string.cancel, null)
                .setNegativeButton(R.string.action_sign_out, (dialog, which) -> signOut())
                .create().show();
    }

    @OnClick(R.id.deleteAccountSIV)
    public void onDeleteAccountClick() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.guide_delete_account)
                .setTitle(R.string.title_delete_account)
                .setPositiveButton(android.R.string.cancel, null)
                .setNegativeButton(R.string.delete, (dialog, which) -> {
                    deletingAccount = true;
                    //Client.getInstance().delete(Client.absoluteUrl(BackEnd.EndPoints.DELETE_ACCOUNT), AccountActivity.this);
                })
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropUtils.REQUEST_PICK && resultCode == RESULT_OK) {
            //verifyImageSize(result.getData());
            beginCrop(data.getData());
        }/* else if (requestCode == CropUtils.REQUEST_CROP && resultCode == RESULT_OK) {
            handleCrop(resultCode, data);
        }*/
    }

    private void handleCrop(int resultCode, Intent result) {
        /*if (resultCode == RESULT_OK) {

            new DialogAsyncTask<Uri, Void, String>() {

                @Override
                protected String doInBackground(Uri... params) {
                    return ImageCompressor.getInstance().compressImage(params[0]);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    ppUpdateImagePath = s;
                    Client.getInstance().updateProfilePhoto(AccountActivity.this, s);
                }
            }.execute(CropUtils.getOutput(result));

        } else if (resultCode == CropUtils.RESULT_ERROR) {
            Toast.makeText(this, CropUtils.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }*/
    }

    protected void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        //CropUtils.of(source, destination).asSquare().start(this);
    }

    @Override
    public void onConnectionStarted() {
        super.onConnectionStarted();
        if (deletingAccount) {
            showProgressDialog();
        } else {
            ppUpdateProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onErrorResponse(Meta meta) {
        super.onErrorResponse(meta);
        if (!deletingAccount) {
            SimpleStorage.getExternalStorage().deleteDirectory(StorageUtils.getPendingUploadDirectory());
        }
    }

    @Override
    public void onConnectionFailed(int statusCode, JSONObject response) {
        if (deletingAccount) {
            deletingAccount = false;
            super.onConnectionFailed(statusCode, response);
        } else {
            toast(R.string.error_profile_update_failed);
            ppUpdateProgressBar.setVisibility(View.GONE);
            SimpleStorage.getExternalStorage().deleteDirectory(StorageUtils.getPendingUploadDirectory());
        }
    }

    @Override
    protected void onSuccessResponse(JSONObject data, Meta meta) {
        super.onSuccessResponse(data, meta);
        if (deletingAccount) {
            signOut();
        } else {
            SimpleStorage.getExternalStorage().move(
                    new File(ppUpdateImagePath), StorageUtils.getProfilePhotoDirectory(), getUser().getUserId() + ".jpg"
            );
            SimpleStorage.getExternalStorage().deleteDirectory(StorageUtils.getPendingUploadDirectory());
            PrefUtils.getInstance().writeLong(R.string.pref_last_profile_photo_update, new Date().getTime());
            //EventBus.getDefault().post(new LocalUserAvatarChangedEvent());
            mAvatarPIV.loadImageFromNetwork(getUser().getAvatar());
            ppUpdateProgressBar.setVisibility(View.GONE);
        }
    }
}
