package com.vibecampo.android.feed.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vibecampo.android.BuildConfig;
import com.vibecampo.android.R;
import com.vibecampo.android.activity.BaseActivity;
import com.vibecampo.android.image.ImageEditActivity;
import com.vibecampo.android.model.Community;
import com.vibecampo.android.model.Goal;
import com.vibecampo.android.model.User;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;
import com.vibecampo.android.settings.PrefUtils;
import com.vibecampo.android.storage.SimpleStorage;
import com.vibecampo.android.storage.Storage;
import com.vibecampo.android.utils.StorageUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.basecode.images.CollageView;
import ke.co.toshngure.basecode.images.camera.CameraActivity;
import ke.co.toshngure.basecode.images.simplecrop.util.CropUtils;
import ke.co.toshngure.basecode.log.BeeLog;
import ke.co.toshngure.basecode.networking.ResponseHandler;
import ke.co.toshngure.basecode.showcase.MaterialShowcaseSequence;
import ke.co.toshngure.basecode.showcase.MaterialShowcaseView;
import ke.co.toshngure.basecode.showcase.ShowcaseConfig;
import ke.co.toshngure.basecode.showcase.shape.RectangleShape;
import ke.co.toshngure.basecode.utils.BaseUtils;

public class PostVibeActivity extends BaseActivity  {

    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_COMMUNITY = "extra_community";
    private static final String EXTRA_GOAL = "extra_goal";
    private static final String EXTRA_STORY_TYPE = "extra_story_type";

    private static final int REQUEST_CAMERA = 5;
    private static final String TAG = PostVibeActivity.class.getSimpleName();
    private static final int EDIT_REQUEST = 15;
    private static final int RE_EDIT_REQUEST = 20;

    @BindView(R.id.targetTV)
    TextView targetTV;

    private int mBeingReedited = -1;

    @BindView(R.id.collageView)
    CollageView mCollageView;

    @BindView(R.id.selectedPhotosTV)
    TextView selectedPhotosTV;

    @BindView(R.id.textET)
    EmojiEditText mTextET;

    @BindView(R.id.titleET)
    EditText mTitleET;


    private ProgressDialog mProgressDialog;
    private List<String> mSelectedPhotos = new ArrayList<>();

    private User mTargetUser;
    private Community mTargetCommunity;
    private String mStoryType;

    private EmojiPopup mEmojiPopup;
    @BindView(R.id.emojiButton)
    ImageButton mEmojiButton;
    @BindView(R.id.rootView)
    View mRootView;

    @BindView(R.id.contentView)
    FrameLayout contentView;


    @Override
    public void onBackPressed() {
        if (mEmojiPopup != null && mEmojiPopup.isShowing()) {
            mEmojiPopup.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        if (mEmojiPopup != null) {
            mEmojiPopup.dismiss();
        }

        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_vibe);

        ButterKnife.bind(this);

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "new_vibe_instructions_v2");
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(300);
        config.setShape(new RectangleShape(new Rect(8, 8, 8, 8), true));
        sequence.setConfig(config);
        sequence.addSequenceItem(new MaterialShowcaseView.Builder(this)
                .setTarget(findViewById(R.id.photosBtn))
                .setTitleText("Select photo from gallery")
                .setContentText("Click this button to select a photo from your gallery images.")
                .setDismissText("GOT IT")
                .build())
                .addSequenceItem(new MaterialShowcaseView.Builder(this)
                        .setTarget(findViewById(R.id.cameraBtn))
                        .setTitleText("Capture image from camera")
                        .setContentText("Click this button to capture a photo with the camera.")
                        .setDismissText("GOT IT")
                        .build())

                .start();

        mCollageView.setUseFirstAsHeader(false);
        mCollageView.setDefaultPhotosForLine(2);

        mCollageView.setOnPhotoClickListener(this::showPhotoOptions);

        if (getIntent().getExtras() != null) {
            mTargetCommunity = getIntent().getExtras().getParcelable(EXTRA_COMMUNITY);
            mStoryType = getIntent().getExtras().getString(EXTRA_STORY_TYPE, "Me");
            mTargetUser = getIntent().getExtras().getParcelable(EXTRA_USER);
            if (mTargetCommunity != null) {
                targetTV.setText(getString(R.string.posting_to, mTargetCommunity.getName()));
            } else if (mTargetUser != null) {
                targetTV.setText(getString(R.string.posting_to, "User Name"));
            }
        } else {
            targetTV.setText(getString(R.string.posting_to, "your timeline"));
        }

        mTextET.setEmojiSize(BaseUtils.dpToPx(18));
        mEmojiButton = findViewById(R.id.emojiButton);
        mEmojiPopup = EmojiPopup.Builder.fromRootView(mRootView)
                .setOnEmojiPopupShownListener(() -> mEmojiButton.setImageResource(R.drawable.ic_keyboard_black_24dp))
                .setOnEmojiPopupDismissListener(() -> mEmojiButton.setImageResource(R.drawable.ic_insert_emoticon_black_24dp))
                .build(mTextET);
        mEmojiButton.setOnClickListener(view -> mEmojiPopup.toggle());

        if (BuildConfig.DEBUG) {
            BaseUtils.cacheInput(mTitleET, R.string.pref_new_vibe_title, PrefUtils.getInstance());
            BaseUtils.cacheInput(mTextET, R.string.pref_new_vibe_text, PrefUtils.getInstance());
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, PostVibeActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, Community community, String storyType) {
        Intent starter = new Intent(context, PostVibeActivity.class);
        starter.putExtra(EXTRA_COMMUNITY, community);
        starter.putExtra(EXTRA_STORY_TYPE, storyType);
        context.startActivity(starter);
    }

    public static void start(Context context, Community community, Goal goal) {
        Intent starter = new Intent(context, PostVibeActivity.class);
        starter.putExtra(EXTRA_COMMUNITY, community);
        starter.putExtra(EXTRA_GOAL, goal);
        context.startActivity(starter);
    }

    public static void start(Context context, User user) {
        Intent starter = new Intent(context, PostVibeActivity.class);
        starter.putExtra(EXTRA_USER, user);
        context.startActivity(starter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if ((requestCode == REQUEST_CAMERA) && (resultCode == Activity.RESULT_OK)) {
            ImageEditActivity.start(this, data.getData(), EDIT_REQUEST);
        } else if ((requestCode == CropUtils.REQUEST_PICK) && (resultCode == Activity.RESULT_OK)) {
            ImageEditActivity.start(this, data.getData(), EDIT_REQUEST);
        } else if ((requestCode == EDIT_REQUEST) && (resultCode == Activity.RESULT_OK)) {
            mSelectedPhotos.add(data.getData().getPath());
            mCollageView.loadFromMediaStore(mSelectedPhotos);
            if (selectedPhotosTV.getVisibility() != View.VISIBLE) {
                selectedPhotosTV.setVisibility(View.VISIBLE);
            }
        } else if ((requestCode == RE_EDIT_REQUEST) && (resultCode == Activity.RESULT_OK)) {
            if (mBeingReedited > 0 && mSelectedPhotos.size() < mBeingReedited) {
                mSelectedPhotos.remove(mBeingReedited);
                mSelectedPhotos.add(data.getData().getPath());
                mCollageView.loadFromMediaStore(mSelectedPhotos);
                if (selectedPhotosTV.getVisibility() != View.VISIBLE) {
                    selectedPhotosTV.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    @OnClick(R.id.photosBtn)
    public void pickImage() {
        CropUtils.pickImage(this);
    }

    @OnClick(R.id.cameraBtn)
    public void takePicture() {
        Intent startCustomCameraIntent = new Intent(this, CameraActivity.class);
        startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);
    }

    private void showPhotoOptions(final int position) {

        new AlertDialog.Builder(this)
                .setItems(new CharSequence[]{"Edit", "Remove"}, (dialog, which) -> {
                    switch (which) {
                        case 1:
                            File f = new File(mSelectedPhotos.get(position));
                            if (f.delete()) {
                                mSelectedPhotos.remove(position);
                                mCollageView.loadFromNetwork(mSelectedPhotos);
                                if ((selectedPhotosTV.getVisibility() != View.GONE) && (mSelectedPhotos.size() == 0)) {
                                    selectedPhotosTV.setVisibility(View.GONE);
                                }
                            }
                            break;
                        case 0:
                            File file = new File(mSelectedPhotos.get(position));
                            mBeingReedited = position;
                            ImageEditActivity.start(getThis(), Uri.fromFile(file), RE_EDIT_REQUEST);
                            break;
                    }
                })
                .create().show();
    }


    @OnClick({R.id.sendBtn})
    public void sendVibe(View view) {
        if (!TextUtils.isEmpty(mTextET.getText().toString()) || mSelectedPhotos.size() > 0) {
            hideKeyboardFrom(mTextET);
            connect();
        } else {
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            Snackbar.make(view, "Your new vibe must have a photo, text or both!", Snackbar.LENGTH_INDEFINITE)
            .setAction(android.R.string.ok, view1 -> {});
        }
    }


    @Override
    public void connect() {
        RequestParams params = new RequestParams();
        params.put(BackEnd.Params.QUERY, BackEnd.QUERY.POST_VIBE);
        params.put(BackEnd.Params.TEXT, mTextET.getText().toString());
        params.put(BackEnd.Params.TITLE, mTitleET.getText().toString());

        if (mTargetCommunity != null) {
            params.put(BackEnd.Params.TO_COMMUNITY, 1);
            params.put(BackEnd.Params.STORY_TYPE, mStoryType);
            params.put(BackEnd.Params.RECIPIENT_ID, mTargetCommunity.getId());
        } else {
            params.put(BackEnd.Params.TO_COMMUNITY, 0);
            params.put(BackEnd.Params.RECIPIENT_ID, 0);
        }

        if (mTargetUser != null) {
            params.put(BackEnd.Params.TO_COMMUNITY, 0);
            params.put(BackEnd.Params.RECIPIENT_ID, mTargetUser.getUserId());
        }

        List<File> files = new ArrayList<>();
        for (String path : mSelectedPhotos) {
            files.add(new File(path));
        }
        try {
            params.put(BackEnd.Params.PHOTOS, files.toArray(new File[files.size()]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            String url = Client.absoluteUrl(BackEnd.EndPoints.POST);
            BeeLog.i(TAG, String.valueOf(params));
            Client.getInstance().getClient().post(url, params, new ResponseHandler(this));
        }
    }

    @Override
    public void onConnectionStarted() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.message_sending));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void onConnectionFailed(int statusCode, JSONObject jsonObject) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.connection_timed_out)
                .setPositiveButton(R.string.retry, (dialogInterface, i) -> connect())
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> this.finish())
                .setCancelable(false)
                .show();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onConnectionSuccess(JSONObject jsonObject) {
        BeeLog.i(TAG, "onConnectionSuccess");
        new AsyncTask<JSONObject, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog.setMessage(getString(R.string.message_waiting));
            }

            @Override
            protected Void doInBackground(JSONObject... params) {
                //Delete photos
                Storage storage = SimpleStorage.getExternalStorage();
                storage.deleteDirectory(StorageUtils.getPendingUploadDirectory());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mProgressDialog.dismiss();
                BeeLog.d(TAG, "onPostExecute");
                PostVibeActivity.this.finish();
            }

        }.execute(jsonObject);
    }

    @Override
    public void onConnectionProgress(int progress) {
    }

    @Override
    public Context getListenerContext() {
        return getThis();
    }

}
